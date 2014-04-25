package de.simmft.core.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class FileMoverStrategyMock implements FileMoverStrategy{
   private static Logger logger = LoggerFactory.getLogger(FileMoverStrategyMock.class);

   @Override
   public void atomicMove(String outbox, String inbox) {
      logger.info("move: " + outbox + " -> " + inbox);
   }

}
