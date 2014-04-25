package de.simmft.core.routing;

import java.util.List;

public interface OutboxListProducer {
   public List<String> produceOutboxList();
}
