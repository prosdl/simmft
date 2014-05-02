package de.simmft.storage.api;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.StreamingOutput;

import de.simmft.common.path.MftPath;

public interface StorageManager {
   public StreamingOutput readFile(String mftAgentId, String jobUri, String transferUri) throws StorageException;
   public FileInfo storeFile(InputStream is, String mftAgentId, String jobUri) throws StorageException;
   void atomicMove(MftPath outbox, String inbox) throws StorageException;
   List<MftPath> getOutboxList() throws StorageException;
}
