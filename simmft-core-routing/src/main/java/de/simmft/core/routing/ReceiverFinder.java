package de.simmft.core.routing;

import java.util.List;

import de.simmft.common.path.MftPath;

public interface ReceiverFinder {
   public List<String> findReceiverForJob(String jodUUID) throws MftRoutingException;
}
