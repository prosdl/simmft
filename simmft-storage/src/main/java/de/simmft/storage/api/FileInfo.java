package de.simmft.storage.api;

import java.util.Date;

public interface FileInfo {
   public String getFilename();
   public Date getCreationTime();
   public String getOwner();
   public Long getSize();
   public String getFileUri();
}
