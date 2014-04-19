package de.simmft.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.simmft.core.model.Permission.PermissionEnum;

@Entity
public class Role {

   @Id
   private String name;

   @ManyToMany(cascade={})
   private Set<Permission> permissions;
   
   public Role() {
   }

   public Role(String name, PermissionEnum...types) {
      this.name = name;
      this.permissions = new HashSet<>();
      for (PermissionEnum type: types) {
         permissions.add(new Permission(type));
      }
   }
   
   public Set<Permission> getPermissions() {
      return permissions;
   }

   public void setPermissions(Set<Permission> permissions) {
      this.permissions = permissions;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
