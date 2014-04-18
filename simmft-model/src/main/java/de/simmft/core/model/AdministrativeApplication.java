package de.simmft.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdministrativeApplication {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   
   @Column(unique=true,nullable=false)
   private String name;
   private String description;
   
   
   public AdministrativeApplication() {
      
   }
   
   public AdministrativeApplication(String name) {
      this.name = name;
   }
   
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
   
   
}
