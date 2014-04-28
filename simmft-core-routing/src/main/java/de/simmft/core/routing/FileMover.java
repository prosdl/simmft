package de.simmft.core.routing;

import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;

@Component("fileMover")
public class FileMover {
   private static Logger logger = LoggerFactory.getLogger(FileMover.class);
   
   @Autowired
   private FileMoverStrategy fileMoverStrategy;

   public void move(@Body MftPath from, @Header("to") List<String> to) {
      logger.info(String.format("move ('%s', '%s')", from,to));
      
      for (String receiver: to) {
         try {
            fileMoverStrategy.atomicMove(from, receiver);
         } catch (MftRoutingException e) {
            throw new RuntimeException(e);
         }
      }
      
   }
}
