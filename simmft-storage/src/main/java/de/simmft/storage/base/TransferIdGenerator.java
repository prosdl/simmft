package de.simmft.storage.base;

import java.util.UUID;

import de.simmft.common.path.MftPath;

public class TransferIdGenerator {
   public static String generateFileName() {
      return new StringBuilder(UUID.randomUUID().toString()).append(MftPath.MFT_EXTENSION).toString();
   }
}
