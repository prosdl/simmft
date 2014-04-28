package de.simmft.core.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;

@Component
@Profile("mock")
public class FileMoverStrategyMock implements FileMoverStrategy{
   private static Logger logger = LoggerFactory.getLogger(FileMoverStrategyMock.class);

   @Override
   public void atomicMove(MftPath outbox, String receiver) {
      logger.info("move: " + outbox + " -> " + receiver);
   }

}
