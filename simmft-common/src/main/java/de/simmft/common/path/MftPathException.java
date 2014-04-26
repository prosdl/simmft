package de.simmft.common.path;

/**
 * Exception indicates problems while creating {@link MftPath} objects.
 * 
 * @see MftPath
 * 
 * @author prosdl
 *
 */
@SuppressWarnings("serial")
public class MftPathException extends Exception {

   public MftPathException() {
      super();
   }

   public MftPathException(String message, Throwable cause) {
      super(message, cause);
   }

   public MftPathException(String message) {
      super(message);
   }

   public MftPathException(Throwable cause) {
      super(cause);
   }

}
