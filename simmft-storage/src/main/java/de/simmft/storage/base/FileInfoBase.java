package de.simmft.storage.base;

import java.util.Date;

import de.simmft.storage.api.FileInfo;

public class FileInfoBase implements FileInfo{
   private String fileName;
   private Date creationTime;
   private String owner;
   private Long size;
   private String fileUri;
   
   public FileInfoBase() {
   }

   public FileInfoBase(String fileName) {
      this.setFileName(fileName);
   }

   @Override
   public String getFilename() {
      return getFileName();
   }
   @Override
   public Date getCreationTime() {
      return creationTime;
   }
   public void setCreationTime(Date creationTime) {
      this.creationTime = creationTime;
   }
   @Override
   public String getOwner() {
      return owner;
   }
   public void setOwner(String owner) {
      this.owner = owner;
   }
   @Override
   public Long getSize() {
      return size;
   }
   public void setSize(Long size) {
      this.size = size;
   }

   public String getFileUri() {
      return fileUri;
   }

   public void setFileUri(String fileUri) {
      this.fileUri = fileUri;
   }

   public String getFileName() {
      return fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }


}
