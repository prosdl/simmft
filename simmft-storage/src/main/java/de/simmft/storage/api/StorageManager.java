package de.simmft.storage.api;

import java.io.InputStream;

import javax.ws.rs.core.StreamingOutput;

public interface StorageManager {
   public StreamingOutput readFile(String mftAgentId, String jobUri, String transferUri) throws StorageException;
   public FileInfo storeFile(InputStream is, String mftAgentId, String jobUri) throws StorageException;
}
