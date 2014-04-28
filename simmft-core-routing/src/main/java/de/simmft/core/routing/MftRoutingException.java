package de.simmft.core.routing;

@SuppressWarnings("serial")
public class MftRoutingException extends Exception {

   public MftRoutingException() {
      super();
   }

   public MftRoutingException(String message, Throwable cause) {
      super(message, cause);
   }

   public MftRoutingException(String message) {
      super(message);
   }

   public MftRoutingException(Throwable cause) {
      super(cause);
   }

}
