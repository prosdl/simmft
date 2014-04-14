package de.simmft.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
   @Id
   private String username;
   private String email;
}
