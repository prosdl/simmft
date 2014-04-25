package de.simmft.core.routing;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class FileMoverMock {
   private static Logger logger = LoggerFactory.getLogger(FileMoverMock.class);

   public void move(@Body String from, @Header("to") String to) {
      logger.info(String.format("move ('%s', '%s')", from,to));
   }
}
