package de.simmft.core.routing;

import de.simmft.common.path.MftPath;

public interface FileMoverStrategy {
   public void atomicMove(MftPath outbox, String mftAgentReceiverId) throws MftRoutingException;
}
