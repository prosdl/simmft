package de.simmft.core.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleBeanImpl implements SimpleBean {
   private static final Logger logger = LoggerFactory.getLogger(SimpleBeanImpl.class);
   
   @Override
   public String echo (String s) {
      logger.info("echo('"  + s + "')");
      return "echo '" + s + "'";
   }
}
