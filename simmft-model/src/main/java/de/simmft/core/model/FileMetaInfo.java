package de.simmft.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @deprecated info should be in the physical storage
 */
@Deprecated
@Entity
public class FileMetaInfo {
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   private String uuid;
   
   private Date created;
   
   @ManyToOne
   private MftAgent owner;
   private Long size;
   
   private String storageLocation;
   
   public String getUuid() {
      return uuid;
   }
   public void setUuid(String uuid) {
      this.uuid = uuid;
   }
   public Date getCreated() {
      return created;
   }
   public void setCreated(Date created) {
      this.created = created;
   }
   public MftAgent getOwner() {
      return owner;
   }
   public void setOwner(MftAgent owner) {
      this.owner = owner;
   }
   public Long getSize() {
      return size;
   }
   public void setSize(Long size) {
      this.size = size;
   }
   public String getStorageLocation() {
      return storageLocation;
   }
   public void setStorageLocation(String storageLocation) {
      this.storageLocation = storageLocation;
   }
   
   
}
