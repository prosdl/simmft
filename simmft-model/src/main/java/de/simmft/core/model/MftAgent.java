package de.simmft.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MftAgent extends SelfDescribingResource {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   
   private String name;
   private String description;
   private String hostServerDns;
   private Date registered;
   
   
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }
   public String getHostServerDns() {
      return hostServerDns;
   }
   public void setHostServerDns(String hostServerDns) {
      this.hostServerDns = hostServerDns;
   }
   public Date getRegistered() {
      return registered;
   }
   public void setRegistered(Date registered) {
      this.registered = registered;
   }
   
   
}
