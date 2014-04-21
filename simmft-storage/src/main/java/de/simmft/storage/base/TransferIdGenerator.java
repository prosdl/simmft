package de.simmft.storage.base;

import java.util.UUID;

public class TransferIdGenerator {
   public static final String MFT_FILE_EXTENSION = ".mft";

   public static String generateFileName() {
      return new StringBuilder(UUID.randomUUID().toString()).append(MFT_FILE_EXTENSION).toString();
   }
}
