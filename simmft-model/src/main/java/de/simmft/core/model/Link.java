package de.simmft.core.model;

/**
 * A link to actions on the resource.
 * 
 * @author prosdl
 *
 */
public class Link {
   public static final String REL_SELF = "self";
   
   private String rel;
   private String href;
   
   public Link() {
      rel = REL_SELF;
   }
   
   public Link(String href) {
      this.rel = REL_SELF;
      this.href = href;
   }
   
   public Link(String rel, String href) {
      this.rel = rel;
      this.href = href;
   }

   public String getRel() {
      return rel;
   }

   public void setRel(String rel) {
      this.rel = rel;
   }

   public String getHref() {
      return href;
   }

   public void setHref(String href) {
      this.href = href;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((href == null) ? 0 : href.hashCode());
      result = prime * result + ((rel == null) ? 0 : rel.hashCode());
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
      Link other = (Link) obj;
      if (href == null) {
         if (other.href != null)
            return false;
      } else if (!href.equals(other.href))
         return false;
      if (rel == null) {
         if (other.rel != null)
            return false;
      } else if (!rel.equals(other.rel))
         return false;
      return true;
   }
   
    
}
