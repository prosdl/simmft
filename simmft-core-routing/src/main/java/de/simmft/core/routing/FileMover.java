package de.simmft.core.routing;

import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("fileMover")
public class FileMover {
   private static Logger logger = LoggerFactory.getLogger(FileMover.class);
   
   @Autowired
   private FileMoverStrategy fileMoverStrategy;

   public void move(@Body String from, @Header("to") List<String> to) {
      logger.info(String.format("move ('%s', '%s')", from,to));
      
      for (String receiver: to) {
         fileMoverStrategy.atomicMove(from, receiver);
      }
      
   }
}
