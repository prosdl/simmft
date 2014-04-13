package de.simmft.core.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("sendjob")
public class SendJob extends Job {
   
   public static enum DeleteAfterTransferPolicy {
      DELETE_FILE,
      ARCHIVE_FILE,
      DO_NOTHING
   }
   

   
   private String srcDir;
   private String srcFilename;
   
   @Enumerated(EnumType.STRING)
   private DeleteAfterTransferPolicy deleteAfterTransferPolicy;
   
   
   public String getSrcDir() {
      return srcDir;
   }
   public void setSrcDir(String srcDir) {
      this.srcDir = srcDir;
   }
   public String getSrcFilename() {
      return srcFilename;
   }
   public void setSrcFilename(String srcFilename) {
      this.srcFilename = srcFilename;
   }
   public DeleteAfterTransferPolicy getDeleteAfterTransferPolicy() {
      return deleteAfterTransferPolicy;
   }
   public void setDeleteAfterTransferPolicy(
         DeleteAfterTransferPolicy deleteAfterTransferPolicy) {
      this.deleteAfterTransferPolicy = deleteAfterTransferPolicy;
   }

}
