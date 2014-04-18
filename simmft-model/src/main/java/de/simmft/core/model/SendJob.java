package de.simmft.core.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.simmft.core.model.builder.IBuilder;

@Entity
@DiscriminatorValue("sendjob")
public class SendJob extends Job {
   
   public static class Builder implements IBuilder<SendJob>{
      private String name;
      private String description;
      private String cronExpression;
      private Date created;
      private Boolean useLegacyPGLocking;
      private AdministrativeApplication administrativeApplication;
      private MftAgent from;
      private Set<MftAgent> to;
      private String srcDir;
      private String srcFilename;
      private DeleteAfterTransferPolicy deleteAfterTransferPolicy;

      public static Builder create(String name) {
         Builder builder = new Builder();
         builder.name = name;
         builder.description = "send_job for '" + name + "'" ;
         builder.cronExpression = "0 0 0/1 * * ?";
         builder.created = new Date();
         builder.useLegacyPGLocking = false;
         builder.to = new HashSet<>();
         builder.deleteAfterTransferPolicy = DeleteAfterTransferPolicy.DELETE_FILE;
         return builder;
      }
      
      public Builder withDescription(String description) {
         this.description = description;
         return this;
      }
      
      public Builder withSrcDir(String srcDir) {
         this.srcDir = srcDir;
         return this;
      }
      
      public Builder withSrcFilename(String srcFilename) {
         this.srcFilename = srcFilename;
         return this;
      }
      
      public Builder scheduledAt(String cronExpression) {
         this.cronExpression = cronExpression;
         return this;
      }
      
      public Builder withLegacyPGLocking() {
         this.useLegacyPGLocking = true;
         return this;
      }
      
      public Builder createdAt(Date created) {
         this.created = created;
         return this;
      }
      
      public Builder from(MftAgent from) {
         this.from = from;
         return this;
      }
      
      public Builder to(MftAgent...to) {
         this.to.addAll(Arrays.asList(to));
         return this;
      }
      
      public Builder afterTransfer(DeleteAfterTransferPolicy deleteAfterTransferPolicy) {
         this.deleteAfterTransferPolicy = deleteAfterTransferPolicy;
         return this;
      }
      
      public Builder forApplication(AdministrativeApplication administrativeApplication) {
         this.administrativeApplication = administrativeApplication;
         return this;
      }
      
      public SendJob build() {
         return new SendJob(this);
      }
   }
   
   public static enum DeleteAfterTransferPolicy {
      DELETE_FILE,
      ARCHIVE_FILE,
      DO_NOTHING
   }
   
   private String srcDir;
   private String srcFilename;
   
   @Enumerated(EnumType.STRING)
   private DeleteAfterTransferPolicy deleteAfterTransferPolicy;
   
   public SendJob() {
   }
   
   public SendJob(String name) {
      super(name);
   }
   
   public SendJob(Builder builder) {
      setDeleteAfterTransferPolicy(builder.deleteAfterTransferPolicy);
      setSrcDir(builder.srcDir);
      setSrcFilename(builder.srcFilename);
      setAdministrativeApplication(builder.administrativeApplication);
      setCreated(builder.created);
      setCronExpression(builder.cronExpression);
      setDescription(builder.description);
      setFrom(builder.from);
      setTo(builder.to);
      setName(builder.name);
      setUseLegacyPGLocking(builder.useLegacyPGLocking);
   }
   
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
