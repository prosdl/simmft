package de.simmft.common.path;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Describes a path in the simmft project. A path is a String representation of
 * mft agents, their in- and outboxes and jobs and files insides those
 * mailboxes.
 * 
 * <p/>
 * BNF style path syntax:
 * 
 * <pre>
 * {@code
 * 
 * <path>         ::= <mft-agent-id> [ "/" <box>  ["/" <job-uuid> ["/" <file-uuid> ".mft" ]]]  
 * <mft-agent-id> ::= <xalpha-num> <xalpha-nums>
 * <xalpha-num>   ::= "A"| ... |"Z"| "a" | ... | "z" | "0" | ... | "9" | "-" | "_"  
 * <xalpha-nums>  ::= } { &lt;xalpha-num&gt; } 
 * {@code
 * <box>          ::= "inbox" | "outbox"
 * <job-uuid>     ::= <uuid>
 * <file-uuid>    ::= <uuid>
 * }
 * </pre>
 * 
 * <p/>
 * 
 * <b>Examples</b>:
 * 
 * <pre>
 * my-mft-sender/outbox/07a0510-3b13-4b17-98e3-b5d91109d8ce
 * mft-receiver-localhost/inbox/07a0510-3b13-4b17-98e3-b5d91109d8ce/668c34bc-a431-415c-8448-215104f0ca43.mft
 * some_receiver
 * some_receiver/inbox
 * </pre>
 * 
 * @author prosdl
 * 
 */
public class MftPath {
   private String mftAgentIdSegment;
   private MailBox boxSegment;
   private UUID jobUUIDSegment;
   private UUID fileUUIDSegment;

   /**
    * Enumeration for the standard mailboxes in simmft.
    * 
    */
   public static enum MailBox {
      INBOX("inbox"), OUTBOX("outbox");
      private String value;
      private static Map<String, MailBox> map = new HashMap<>();

      private MailBox(String value) {
         this.value = value;
      }
      
      static {
         for (MailBox m: values()) {
            map.put(m.value, m);
         }
      }

      @Override
      public String toString() {
         return value;
      }
      
      public static MailBox fromString(String s) throws MftPathException {
         if (map.containsKey(s)) {
            return map.get(s);
         } else {
            throw new MftPathException("Illegal syntax: '" + s + "' is not a valid mailbox");
         }
      }
   }

   public MftPath(String mftAgentId) throws MftPathException {
      validateMftAgentSyntax(mftAgentId);
      this.setMftAgentIdSegment(mftAgentId);
   }

   public MftPath(String mftAgentId, MailBox box) throws MftPathException {
      this(mftAgentId);
      this.boxSegment = box;
   }

   public MftPath(String mftAgentId, MailBox box, UUID jobUUID)
         throws MftPathException {
      this(mftAgentId, box);
      this.jobUUIDSegment = jobUUID;
   }

   public MftPath(String mftAgentId, MailBox box, String jobUUID)
         throws MftPathException {
      this(mftAgentId, box);
      setJobUUIDSegment(jobUUID);
   }

   public MftPath(String mftAgentId, MailBox box, UUID jobUUID, UUID fileUUID)
         throws MftPathException {
      this(mftAgentId, box, jobUUID);
      this.fileUUIDSegment = fileUUID;
   }

   public MftPath(String mftAgentId, MailBox box, String jobUUID,
         String fileUUID) throws MftPathException {
      this(mftAgentId, box, jobUUID);
      setFileUUIDSegment(fileUUID);
   }

   public static void validateMftAgentSyntax(String mftAgentIdSegment)
         throws MftPathException {
      if (mftAgentIdSegment == null || mftAgentIdSegment.isEmpty()) {
         throw new MftPathException(
               "Illegal syntax for mft-agent-id: can't be empty");
      }
      if (!mftAgentIdSegment.matches("[a-zA-Z0-9_\\-]+")) {
         throw new MftPathException("Illegal syntax for mft-agent-id: '"
               + mftAgentIdSegment + "'");
      }
   }

   public static MftPath fromString(String path) throws MftPathException {
      if (path == null || path.isEmpty()) {
         throw new MftPathException("Path can't be empty");
      }
      String[] segments = path.split("/");
      MftPath mftPath = new MftPath(segments[0]);
      if (segments.length == 1) {
         return mftPath;
      }
      mftPath.setBoxSegment(segments[1]);
      if (segments.length == 2) {
         return mftPath;
      }
      mftPath.setJobUUIDSegment(segments[2]);
      if (segments.length == 3) {
         return mftPath;
      }
      mftPath.setJobUUIDSegment(segments[3]);
      if (segments.length == 4) {
         return mftPath;
      }

      throw new MftPathException("Illegal syntax: too many segments in '" + path + "'");
   }

   public String getMftAgentIdSegment() {
      return mftAgentIdSegment;
   }

   public void setMftAgentIdSegment(String mftAgentIdSegment)
         throws MftPathException {
      validateMftAgentSyntax(mftAgentIdSegment);
      this.mftAgentIdSegment = mftAgentIdSegment;
   }

   public MailBox getBoxSegment() {
      return boxSegment;
   }

   public void setBoxSegment(MailBox boxSegment) {
      this.boxSegment = boxSegment;
   }
   
   public void setBoxSegment(String boxSegment) throws MftPathException {
      this.boxSegment = MailBox.fromString(boxSegment);
   }

   public UUID getJobUUIDSegment() {
      return jobUUIDSegment;
   }

   public void setJobUUIDSegment(UUID jobUUIDSegment) {
      this.jobUUIDSegment = jobUUIDSegment;
   }

   public void setJobUUIDSegment(String jobUUIDSegment) throws MftPathException {
      try {
         this.jobUUIDSegment = UUID.fromString(jobUUIDSegment);
      } catch (IllegalArgumentException e) {
         throw new MftPathException("Illegal syntax for jobUUID: "
               + e.getMessage(), e);
      }
   }

   public UUID getFileUUIDSegment() {
      return fileUUIDSegment;
   }

   public void setFileUUIDSegment(UUID fileUUIDSegment) {
      this.fileUUIDSegment = fileUUIDSegment;
   }

   public void setFileUUIDSegment(String fileUUIDSegment)
         throws MftPathException {
      try {
         this.fileUUIDSegment = UUID.fromString(fileUUIDSegment);
      } catch (IllegalArgumentException e) {
         throw new MftPathException("Illegal syntax for jobUUID: "
               + e.getMessage(), e);
      }
   }

}
