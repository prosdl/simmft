package de.simmft.core.services.exception;

@SuppressWarnings("serial")
public class MftCoreServiceException extends Exception {

   public MftCoreServiceException() {
      super();
   }

   public MftCoreServiceException(String message, Throwable cause) {
      super(message, cause);
   }

   public MftCoreServiceException(String message) {
      super(message);
   }

   public MftCoreServiceException(Throwable cause) {
      super(cause);
   }

}
