package de.simmft.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

@Entity
public class OauthClientCredentials {
   @Id
   private String clientId;
   private String hashedClientPassword;
   
   public OauthClientCredentials() {
   }
   
   public OauthClientCredentials(String clientId, String clearPassword) {
      this.clientId = clientId;
      ShaPasswordEncoder passowrdEncoder = new ShaPasswordEncoder(256);
      passowrdEncoder.setEncodeHashAsBase64(true);
      this.hashedClientPassword = passowrdEncoder.encodePassword(clearPassword, null);
   }
   
   public String getClientId() {
      return clientId;
   }
   public void setClientId(String clientId) {
      this.clientId = clientId;
   }
   public String getHashedClientPassword() {
      return hashedClientPassword;
   }
   public void setHashedClientPassword(String hashedClientPassword) {
      this.hashedClientPassword = hashedClientPassword;
   }
   
   
}
