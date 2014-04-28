package de.simmft.core.routing;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;
import de.simmft.common.path.MftPathException;

@Component
@Profile("mock")
public class OutboxListProducerMock implements OutboxListProducer {
   @Override
   public List<MftPath> produceOutboxList() throws MftRoutingException {
      try {
         return Arrays.asList(
               new MftPath("mft-agent-sender-001", MftPath.MailBox.OUTBOX, UUID.randomUUID()),
               new MftPath("mft-agent-sender-002", MftPath.MailBox.OUTBOX, UUID.randomUUID())
               );
      } catch (MftPathException e) {
         throw new MftRoutingException(e);
      }
      
   }

}
