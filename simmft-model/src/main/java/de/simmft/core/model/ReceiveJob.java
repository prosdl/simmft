package de.simmft.core.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.simmft.core.model.builder.IBuilder;

@Entity
@DiscriminatorValue("receivejob")
public class ReceiveJob extends Job {
   public static class Builder implements IBuilder<ReceiveJob>{
      private String name;
      private String description;
      private String cronExpression;
      private Date created;
      private Boolean useLegacyPGLocking;
      private AdministrativeApplication administrativeApplication;
      private MftAgent from;
      private Set<MftAgent> to;
      private String targetDir;
      private String targetFilename;
      private MultipleMatchingFilesPolicy matchingFilesPolicy;
      private OnFilePresentPolicy onFilePresentPolicy;
      private SendJob referringSendJob;
      
    
      public static Builder create(String name) {
         Builder builder = new Builder();
         builder.name = name;
         builder.description = "send_job for '" + name + "'" ;
         builder.cronExpression = "0 0 0/1 * * ?";
         builder.created = new Date();
         builder.useLegacyPGLocking = false;
         builder.to = new HashSet<>();
         builder.matchingFilesPolicy = MultipleMatchingFilesPolicy.GET_ALL;
         builder.onFilePresentPolicy = OnFilePresentPolicy.REPLACE_FILE;
         return builder;
      }
      
      public Builder whenMultipleFilesInStore(MultipleMatchingFilesPolicy policy) {
         this.matchingFilesPolicy = policy;
         return this;
      }
      
      public Builder whenFileIsLocallyPresent(OnFilePresentPolicy onFilePresentPolicy) {
         this.onFilePresentPolicy = onFilePresentPolicy;
         return this;
      }
      
      public Builder withDescription(String description) {
         this.description = description;
         return this;
      }
      
      public Builder withTargetDir(String targetDir) {
         this.targetDir = targetDir;
         return this;
      }
      
      public Builder withTargetFilename(String targetFilename) {
         this.targetFilename = targetFilename;
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
      
      public Builder referringTo(SendJob sendJob) {
         this.referringSendJob = sendJob;
         return this;
      }
      
      public Builder forApplication(AdministrativeApplication administrativeApplication) {
         this.administrativeApplication = administrativeApplication;
         return this;
      }
      
      public ReceiveJob build() {
         return new ReceiveJob(this);
      }
   }

   public static enum MultipleMatchingFilesPolicy {
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
   private MultipleMatchingFilesPolicy matchingFilesPolicy;
   
   @Enumerated(EnumType.STRING)
   private OnFilePresentPolicy onFilePresentPolicy;
   
   @JsonProperty(value = "referring-sendjob-uuid")
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
   @JsonIdentityReference(alwaysAsId = true)    
   @OneToOne(optional=true)
   private SendJob referringSendJob;
   
   public ReceiveJob() {
      
   }
   
   public ReceiveJob(String name) {
      super(name);
   }
  
   public ReceiveJob(Builder builder) {
      setAdministrativeApplication(builder.administrativeApplication);
      setCreated(builder.created);
      setCronExpression(builder.cronExpression);
      setDescription(builder.description);
      setFrom(builder.from);
      setTo(builder.to);
      setName(builder.name);
      setUseLegacyPGLocking(builder.useLegacyPGLocking);
      setTargetDir(builder.targetDir);
      setTargetFilename(builder.targetFilename);
      setMatchingFilesPolicy(builder.matchingFilesPolicy);
      setOnFilePresentPolicy(builder.onFilePresentPolicy);
      setReferringSendJob(builder.referringSendJob);
   }
   
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
   public MultipleMatchingFilesPolicy getMatchingFilesPolicy() {
      return matchingFilesPolicy;
   }
   public void setMatchingFilesPolicy(
         MultipleMatchingFilesPolicy matchingFilesPolicy) {
      this.matchingFilesPolicy = matchingFilesPolicy;
   }
   public OnFilePresentPolicy getOnFilePresentPolicy() {
      return onFilePresentPolicy;
   }
   public void setOnFilePresentPolicy(OnFilePresentPolicy onFilePresentPolicy) {
      this.onFilePresentPolicy = onFilePresentPolicy;
   }

   public SendJob getReferringSendJob() {
      return referringSendJob;
   }

   public void setReferringSendJob(SendJob referringSenderJob) {
      this.referringSendJob = referringSenderJob;
   }
   
   
   
}
