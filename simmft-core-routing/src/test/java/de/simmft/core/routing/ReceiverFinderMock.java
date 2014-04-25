package de.simmft.core.routing;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class ReceiverFinderMock implements ReceiverFinder {

   @Override
   public List<String> findReceiverForJob(String jobUUID) {
      return Arrays.asList(new String[]{"mft-agent-" + jobUUID.substring(0,4)});
   }

}
