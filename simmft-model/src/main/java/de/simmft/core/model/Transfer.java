package de.simmft.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Transfer {
   
   public static enum Status {
      COMPLETED,
      CANCELLED,
      ERROR,
      IN_PROGRESS
   }
   
   public static enum ErrorCode {
      OK(10000),
      SEND_GENERIC_ERROR(20000),
      SEND_FILE_NOT_FOUND(20001),
      SEND_FILE_READ(20002),
      
      RECEIVE_GENERIC_ERROR(30000),
      RECEIVE_FILE_PRESENT(30001),
      RECEIVE_FILE_WRITE(30002)
      ;
      
      private int code;
      private ErrorCode(int code) {
         this.code = code;
      }
      public int getCode() {
         return code;
      }
      
   }
   
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   private String uuid;
   
   private Date started;
   
   private Long filesize;
   
   @Enumerated(EnumType.STRING)
   private Status status;
   
   @Enumerated(EnumType.STRING)
   private ErrorCode errorCode;
   
   private String errorDescription;
   
   @Lob
   private byte[] errorDetails;
   
   
   @ManyToOne(optional=false)
   private Job job;
   
   public Transfer() {
      
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public Date getStarted() {
      return started;
   }

   public void setStarted(Date started) {
      this.started = started;
   }

   public Long getFilesize() {
      return filesize;
   }

   public void setFilesize(Long filesize) {
      this.filesize = filesize;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

   public void setErrorCode(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   public String getErrorDescription() {
      return errorDescription;
   }

   public void setErrorDescription(String errorDescription) {
      this.errorDescription = errorDescription;
   }

   public byte[] getErrorDetails() {
      return errorDetails;
   }

   public void setErrorDetails(byte[] errorDetails) {
      this.errorDetails = errorDetails;
   }

   public Job getJob() {
      return job;
   }

   public void setJob(Job job) {
      this.job = job;
   }
   
   
   
}
