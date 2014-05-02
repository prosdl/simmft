package de.simmft.storage.routing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;
import de.simmft.core.routing.MftRoutingException;
import de.simmft.core.routing.OutboxListProducer;
import de.simmft.storage.api.StorageException;
import de.simmft.storage.api.StorageManager;

@Component
public class OutboxListProducerDefault implements OutboxListProducer {
   
   @Autowired
   private StorageManager storageManager;
   
   @Override
   public List<MftPath> produceOutboxList() throws MftRoutingException {
      try {
         return storageManager.getOutboxList();
      } catch (StorageException e) {
         throw new MftRoutingException(e);
      }
   }

}
