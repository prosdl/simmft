package de.simmft.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class User implements OauthUser {
   @Id
   private String username;
   private String email;
   
   @ManyToOne
   private Role role;
   
   @OneToOne(cascade=CascadeType.ALL)
   private OauthClientCredentials oauthClientCredentials;
   
   public User() {
      
   }
   
   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
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
