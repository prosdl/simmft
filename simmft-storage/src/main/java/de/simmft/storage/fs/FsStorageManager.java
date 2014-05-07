package de.simmft.storage.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;
import de.simmft.common.path.MftPathException;
import de.simmft.storage.api.FileInfo;
import de.simmft.storage.api.StorageException;
import de.simmft.storage.api.StorageManager;
import de.simmft.storage.base.FileInfoBase;
import de.simmft.storage.base.TransferIdGenerator;

@Component
@Profile("storage-fs")
public class FsStorageManager implements StorageManager {
   private static final int FILE_WRITE_BUFFER_SIZE = 1024;

   public static final String OUTBOX_DIRNAME = "outbox";

   private static Logger logger = LoggerFactory
         .getLogger(FsStorageManager.class);

   // FIXME
   private String basePath = "/tmp";

   private String fromBasePath(String... pathSegments) {
      if (pathSegments == null || pathSegments.length == 0) {
         return getBasePath();
      }

      StringBuilder sb = new StringBuilder(getBasePath());
      for (String seg : pathSegments) {
         sb.append(File.separatorChar).append(seg);
      }

      return sb.toString();
   }

   @Override
   public FileInfo storeFile(InputStream is, String mftAgentId, String jobUri)
         throws StorageException {
      String dirpath = fromBasePath(mftAgentId, OUTBOX_DIRNAME, jobUri);

      File dir = new File(dirpath);
      if (!dir.exists()) {
         if (!dir.mkdirs()) {
            throw new StorageException("Couldn't create directory: '" + dirpath
                  + "'");
         }
      }

      String transferFileName = TransferIdGenerator.generateFileName();
      File file = new File(dirpath + File.separatorChar + transferFileName);

      try {
         Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
         StorageException se = new StorageException(
               "Couldn't copy input stream to file: " + e.getMessage(), e);
         logger.error(se.getMessage(), e);
         throw se;
      }

      FileInfoBase info = new FileInfoBase();
      BasicFileAttributes fileAttributes;
      try {
         fileAttributes = Files.readAttributes(file.toPath(),
               BasicFileAttributes.class);
      } catch (IOException e) {
         StorageException se = new StorageException(
               "Error while accessing file attributes: " + e.getMessage(), e);
         logger.error(se.getMessage(), e);
         throw se;
      }
      info.setCreationTime(new Date(fileAttributes.creationTime().toMillis()));
      info.setOwner(mftAgentId);
      info.setSize(fileAttributes.size());
      info.setFileName(transferFileName);
      info.setFileUri(file.toURI().toString());
      return info;
   }

   @Override
   public StreamingOutput readFile(String mftAgentId, String jobUri,
         String transferUri) throws StorageException {
      String pathToFile = fromBasePath(mftAgentId, "inbox", jobUri, transferUri);
      final File file = new File(pathToFile);

      if (!file.exists()) {
         throw new StorageException("File not found: '" + pathToFile + "'");
      }

      return new StreamingOutput() {

         @Override
         public void write(OutputStream os) throws IOException,
               WebApplicationException {
            FileInputStream is = new FileInputStream(file);
            byte[] buffer = new byte[FILE_WRITE_BUFFER_SIZE];
            int len;
            while ((len = is.read(buffer)) != -1) {
               os.write(buffer, 0, len);
            }
            is.close();

            // FIXME remove file
         }
      };
   }

   public Path mftPathToNIOPath(MftPath mftPath) {
      return Paths.get(basePath, mftPath.toSegmentArray());
   }

   public MftPath nioPathToMftPath(Path path) throws MftPathException {
      // FIXME basePath as Path
      int seg = Paths.get(basePath).getNameCount();
      Path sub = path.subpath(seg, path.getNameCount());
      return MftPath.fromString(sub.toString());
   }

   public String getBasePath() {
      return basePath;
   }

   public void setBasePath(String basePath) {
      this.basePath = basePath;
   }

   @Override
   public void atomicMove(MftPath outbox, String mftAgentReceiverId)
         throws StorageException {
      Path source = mftPathToNIOPath(outbox);
      Path target;
      try {
         target = mftPathToNIOPath(new MftPath(mftAgentReceiverId,
               MftPath.MailBox.INBOX, outbox.getJobUUIDSegment()));
      } catch (MftPathException e) {
         throw new StorageException(e);
      }

      if (!Files.exists(target)) {
         try {
            Files.createDirectories(target);
         } catch (IOException e) {
            throw new StorageException(e);
         }
      }

      try (DirectoryStream<Path> filesStream = Files.newDirectoryStream(source)) {

         Iterator<Path> filesIterator = filesStream.iterator();
         while (filesIterator.hasNext()) {
            Path file = filesIterator.next();
            try {
               Files.move(file, target.resolve(file.getFileName()),
                     StandardCopyOption.ATOMIC_MOVE,
                     StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
               throw new StorageException(e);
            }
         }
      } catch (IOException e) {
         throw new StorageException(e);
      }

   }

   @Override
   public List<MftPath> getOutboxList() throws StorageException {
      DirectoryStream.Filter<Path> filledOutboxFilter = new DirectoryStream.Filter<Path>() {

         @Override
         public boolean accept(Path entry) throws IOException {
            Path p = Paths.get(entry.toString(),
                  MftPath.MailBox.OUTBOX.toString());
            return Files.exists(p);
         }
      };

      try (DirectoryStream<Path> stream = Files.newDirectoryStream(
            Paths.get(basePath), filledOutboxFilter)) {
         Iterator<Path> it = stream.iterator();
         List<MftPath> list = new ArrayList<>();
         while (it.hasNext()) {
            Path mftAgentOutboxDir = Paths.get(it.next().toString(),
                  MftPath.MailBox.OUTBOX.toString());
            try (DirectoryStream<Path> jobStream = Files
                  .newDirectoryStream(mftAgentOutboxDir)) {
               Iterator<Path> jobIterator = jobStream.iterator();
               while (jobIterator.hasNext()) {
                  Path pathToJob = jobIterator.next();
                  try {
                     list.add(nioPathToMftPath(pathToJob));
                  } catch (MftPathException | IllegalArgumentException e) {
                     logger.error("Error while scanning outboxes", e);
                  }
               }
            } catch (IOException e) {
               throw new StorageException(e);
            }
         }
         return list;
      } catch (IOException e) {
         throw new StorageException(e);
      }
   }

   public static void main(String[] args) throws StorageException,
         MftPathException {
      FsStorageManager sm = new FsStorageManager();
      // sm.atomicMove(MftPath.fromString("test.mft-agent-sender-1/outbox/1899598a-e235-44f3-a8b6-87999166b279"),
      // "test.mft-agent-sender-2");
      System.out.println(sm.getOutboxList());
   }

}
