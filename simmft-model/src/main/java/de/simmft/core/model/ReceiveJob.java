package de.simmft.core.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("receivejob")
public class ReceiveJob extends Job {
   
   public static enum MutipleMatchingFilesPolicy {
      GET_ALL,
      GET_LATEST,
      ERROR
   }
   
   public static enum OnFilePresentPolicy {
      REPLACE_FILE,
      CANCEL_TRANSFER,
      ERROR,
      MERGE_FILES
   }
   
   private String targetDir;
   private String targetFilename;
   
   @Enumerated(EnumType.STRING)
   private MutipleMatchingFilesPolicy matchingFilesPolicy;
   
   @Enumerated(EnumType.STRING)
   private OnFilePresentPolicy onFilePresentPolicy;
  
   public String getTargetDir() {
      return targetDir;
   }
   public void setTargetDir(String targetDir) {
      this.targetDir = targetDir;
   }
   public String getTargetFilename() {
      return targetFilename;
   }
   public void setTargetFilename(String targetFilename) {
      this.targetFilename = targetFilename;
   }
   public MutipleMatchingFilesPolicy getMatchingFilesPolicy() {
      return matchingFilesPolicy;
   }
   public void setMatchingFilesPolicy(
         MutipleMatchingFilesPolicy matchingFilesPolicy) {
      this.matchingFilesPolicy = matchingFilesPolicy;
   }
   public OnFilePresentPolicy getOnFilePresentPolicy() {
      return onFilePresentPolicy;
   }
   public void setOnFilePresentPolicy(OnFilePresentPolicy onFilePresentPolicy) {
      this.onFilePresentPolicy = onFilePresentPolicy;
   }
   
   
   
}
