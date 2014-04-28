package de.simmft.core.routing;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simmft.common.path.MftPath;

@Component
public class EnrichWithReceiverAggregationStrategy implements
      AggregationStrategy {
   private static Logger logger = LoggerFactory.getLogger(EnrichWithReceiverAggregationStrategy.class);
   
   @Autowired
   private ReceiverFinder receiverFinder;
   

   @Override
   public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
      MftPath pathToJob = (MftPath) oldExchange.getIn().getBody();
      logger.debug("aggregate: " + pathToJob);

      try {
         String jobUUID = pathToJob.getJobUUIDSegment().toString();
         if (jobUUID == null ) throw new IllegalArgumentException("jobuuid is null");

         oldExchange.getIn().setHeader("to", receiverFinder.findReceiverForJob(jobUUID));
         
         return oldExchange;
      } catch (IllegalArgumentException | MftRoutingException e) {
         throw new RuntimeException(e);
      }
   }

}