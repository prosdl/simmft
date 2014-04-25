package de.simmft.core.routing;

import java.util.List;

public interface ReceiverFinder {
   public List<String> findReceiverForJob(String jodUUID);
}
