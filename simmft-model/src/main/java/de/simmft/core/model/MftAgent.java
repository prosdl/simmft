package de.simmft.core.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonValue;

@Entity
public class MftAgent extends SelfDescribingResource implements OauthUser {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   
   @Column(unique=true,nullable=false)
   private String name;
   private String description;
   private String hostServerDns;
   private Date registered;
   private String hostServerOS;
   
   @OneToOne(cascade=CascadeType.ALL)
   private OauthClientCredentials oauthClientCredentials;
   
   @ManyToOne
   private Role role;
   
   public MftAgent() {
      
   }
   
   public MftAgent(String name) {
      this.name = name;
      registered = new Date();
   }
   public MftAgent(String name, OauthClientCredentials credentials) {
      this.name = name;
      registered = new Date();
      this.oauthClientCredentials = credentials;
   }
   
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }
   
   @JsonValue()
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

   public String getHostServerOS() {
      return hostServerOS;
   }

   public void setHostServerOS(String os) {
      this.hostServerOS = os;
   }

   public OauthClientCredentials getOauthClientCredentials() {
      return oauthClientCredentials;
   }

   public void setOauthClientCredentials(OauthClientCredentials oauthClientCredentials) {
      this.oauthClientCredentials = oauthClientCredentials;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }
   
   
}
