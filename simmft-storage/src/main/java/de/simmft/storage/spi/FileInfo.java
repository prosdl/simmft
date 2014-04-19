package de.simmft.storage.spi;

import java.util.Date;

public interface FileInfo {
   public String getUuid();
   public Date getCreationTime();
   public String getOwner();
   public Long getSize();
}
