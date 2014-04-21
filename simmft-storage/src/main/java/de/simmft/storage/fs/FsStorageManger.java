package de.simmft.storage.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.simmft.storage.api.FileInfo;
import de.simmft.storage.api.StorageException;
import de.simmft.storage.api.StorageManager;
import de.simmft.storage.base.FileInfoBase;
import de.simmft.storage.base.TransferIdGenerator;

@Component
@Profile("storage-fs")
public class FsStorageManger implements StorageManager {
   public static final String OUTBOX_DIRNAME = "outbox";

   private static Logger logger = LoggerFactory
         .getLogger(FsStorageManger.class);

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
   public OutputStream readFile(String mftAgentId, String jobUri,
         String transferUri) {
      throw new RuntimeException("not implemented");
   }

   public String getBasePath() {
      return basePath;
   }

   public void setBasePath(String basePath) {
      this.basePath = basePath;
   }

}
