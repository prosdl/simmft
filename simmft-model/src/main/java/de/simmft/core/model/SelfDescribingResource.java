package de.simmft.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A base class for HATEOAS style resources. Entities which are REST resources
 * should subclass this.
 * <p>
 * This maintains a list of {@link Link} objects, that describe the resource. The list
 * itself is transient; it's the REST APIs job, to set the list via {@link #add(Link)}.
 * 
 * @see Link
 * @author prosdl
 *
 */
public class SelfDescribingResource {
   
   @JsonProperty("_links")
   @JsonInclude(Include.NON_EMPTY)
   @Transient
   private List<Link> links = new ArrayList<>();
   
   
   public void add(Link link) {
      this.links.add(link);
   }
   
   public void add(Link... links) {
      this.links.addAll(Arrays.asList(links));
   }
   
   public List<Link> getLinks() {
      return Collections.unmodifiableList(links);
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((links == null) ? 0 : links.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      SelfDescribingResource other = (SelfDescribingResource) obj;
      if (links == null) {
         if (other.links != null)
            return false;
      } else if (!links.equals(other.links))
         return false;
      return true;
   }
   
   
}
