package de.simmft.storage.spi;

import java.io.InputStream;
import java.io.OutputStream;

public interface StorageManager {
   public FileInfo storeFile(InputStream is, MftBox box, String jobUri);
   public OutputStream readFile(MftBox box, String jobUri, String transferUri);
}
