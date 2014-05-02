package de.simmft.storage.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;
import de.simmft.core.routing.FileMoverStrategy;
import de.simmft.core.routing.MftRoutingException;
import de.simmft.storage.api.StorageException;
import de.simmft.storage.api.StorageManager;

@Component
public class FileMoverStrategyDefault implements FileMoverStrategy{
   private static Logger logger = LoggerFactory.getLogger(FileMoverStrategyDefault.class);

   @Autowired
   private StorageManager storageManager;
   
   @Override
   public void atomicMove(MftPath outbox, String mftAgentReceiverId) throws MftRoutingException{
      logger.debug("move: " + outbox + " -> " + mftAgentReceiverId);
      try {
         storageManager.atomicMove(outbox, mftAgentReceiverId);
      } catch (StorageException e) {
         throw new MftRoutingException(e);
      }
   }

}
