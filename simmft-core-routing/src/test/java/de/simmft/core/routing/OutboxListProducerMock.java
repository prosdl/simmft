package de.simmft.core.routing;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class OutboxListProducerMock implements OutboxListProducer {
   @Override
   public List<String> produceOutboxList() {
      return Arrays.asList(
            "mft-agent-sender-001/outbox/" + UUID.randomUUID(),
            "mft-agent-sender-002/outbox/" + UUID.randomUUID());
      
   }

}
