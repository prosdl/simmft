package de.simmft.core.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="jobtype",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("job")
public class Job extends SelfDescribingResource {
   @Id 
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   private String uuid;
   
   @Column(unique=true,nullable=false)
   private String name;
   private String description;
   private String cronExpression;
   private Date created;
   private Boolean useLegacyPGLocking;
   
   @JsonProperty
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
   @JsonIdentityReference(alwaysAsId = true)    
   @ManyToOne
   private AdministrativeApplication administrativeApplication;
   
   @JsonProperty
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
   @JsonIdentityReference(alwaysAsId = true)    
   @ManyToOne()
   private MftAgent from;
   
   @ManyToMany
   private Set<MftAgent> to;
   
   public Job() {
   }
   
   public Job(String name) {
      this.name = name;
      this.created = new Date();
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
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

   public String getCronExpression() {
      return cronExpression;
   }

   public void setCronExpression(String cronExpression) {
      this.cronExpression = cronExpression;
   }

   public AdministrativeApplication getAdministrativeApplication() {
      return administrativeApplication;
   }

   public void setAdministrativeApplication(
         AdministrativeApplication administrativeApplication) {
      this.administrativeApplication = administrativeApplication;
   }

   public MftAgent getFrom() {
      return from;
   }

   public void setFrom(MftAgent from) {
      this.from = from;
   }

   public Set<MftAgent> getTo() {
      return to;
   }

   public void setTo(Set<MftAgent> to) {
      this.to = to;
   }

   public Date getCreated() {
      return created;
   }

   public void setCreated(Date created) {
      this.created = created;
   }

   public Boolean getUseLegacyPGLocking() {
      return useLegacyPGLocking;
   }

   public void setUseLegacyPGLocking(Boolean useLegacyPGLocking) {
      this.useLegacyPGLocking = useLegacyPGLocking;
   }
   
   @JsonProperty
   public String getType() {
      return this.getClass().getSimpleName();
   }
   
}
