package de.simmft.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
public class Permission implements GrantedAuthority {
   public static enum PermissionEnum {
      MFT_GET_JOB_LIST("mft","jobs","get"),
      MFT_WRITE_TO_STORE("mft","storage","write"),
      MFT_READ_FROM_STORE("mft","storage","read"),
      REQUEST_CREDENTIALS("admin", "authentication", "create");

      private String domain;
      private String entity;
      private String operation;

      private PermissionEnum(String domain, String entity, String operation) {
         this.domain = domain;
         this.entity = entity;
         this.operation = operation;
      }

      public String toString() {
         return new StringBuilder(domain).append(':').append(entity)
               .append(':').append(operation).toString();
      }
      
      public static List<PermissionEnum> valuesMatching(String regex) {
         ArrayList<PermissionEnum> list = new ArrayList<>();
         for (PermissionEnum p: values()) {
            if (p.toString().matches(regex)) {
               list.add(p);
            }
         }
         return list;
      }
   }

   @Id
   @Enumerated(EnumType.STRING)
   private PermissionEnum type;

   public Permission() {
   }
   
   @Override
   public String toString() {
      return type.toString();
   }
   
   public Permission(PermissionEnum perm) {
      type = perm;
   }

   public PermissionEnum getType() {
      return type;
   }

   public void setType(PermissionEnum type) {
      this.type = type;
   }

   @Override
   public String getAuthority() {
      return type.toString();
   }
   
   public static Set<Permission> getPermissionsMatching(String regex) {
      Set<Permission> set = new HashSet<>();
      for (PermissionEnum p: PermissionEnum.valuesMatching(regex)) {
         set.add(new Permission(p));
      }
      return set;
   }
}
