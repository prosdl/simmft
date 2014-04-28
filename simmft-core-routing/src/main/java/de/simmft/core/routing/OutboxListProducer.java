package de.simmft.core.routing;

import java.util.List;

import de.simmft.common.path.MftPath;

public interface OutboxListProducer {
   public List<MftPath> produceOutboxList() throws MftRoutingException;
}
