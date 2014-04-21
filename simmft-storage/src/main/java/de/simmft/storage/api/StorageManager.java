package de.simmft.storage.api;

import java.io.InputStream;
import java.io.OutputStream;

public interface StorageManager {
   public OutputStream readFile(String mftAgentId, String jobUri, String transferUri);
   public FileInfo storeFile(InputStream is, String mftAgentId, String jobUri) throws StorageException;
}
