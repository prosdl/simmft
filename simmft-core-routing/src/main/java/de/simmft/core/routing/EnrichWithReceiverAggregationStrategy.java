package de.simmft.core.routing;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;
import de.simmft.common.path.MftPathException;

@Component
public class EnrichWithReceiverAggregationStrategy implements
      AggregationStrategy {
   private static Logger logger = LoggerFactory.getLogger(EnrichWithReceiverAggregationStrategy.class);
   
   @Autowired
   private ReceiverFinder receiverFinder;
   

   @Override
   public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
      String pathToJob = (String) oldExchange.getIn().getBody();

      MftPath path;
      try {
         path = MftPath.fromString(pathToJob);
         String jobUUID = path.getJobUUIDSegment().toString();
         if (jobUUID == null ) throw new IllegalArgumentException("jobuuid is null");

         oldExchange.getIn().setHeader("to", receiverFinder.findReceiverForJob(jobUUID));
         
         return oldExchange;
      } catch (MftPathException | IllegalArgumentException e) {
         logger.error("Exception while getting uuid: " + e.getMessage(), e);
         // TODO camel error handling?
         throw new RuntimeException(e);
      }
   }

}