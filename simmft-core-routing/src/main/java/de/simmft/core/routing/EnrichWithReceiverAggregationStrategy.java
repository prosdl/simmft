package de.simmft.core.routing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrichWithReceiverAggregationStrategy implements
      AggregationStrategy {
   private static Logger logger = LoggerFactory.getLogger(EnrichWithReceiverAggregationStrategy.class);
   
   @Autowired
   private ReceiverFinder receiverFinder;
   

   @Override
   public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
      String pathToJob = (String) oldExchange.getIn().getBody();

      // FIXME -> commons
      // mft/outbox/{jobid}
      String regex = ".*/outbox/([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(pathToJob);
      String jobUUID = null;
      if (matcher.find()) {
         jobUUID = matcher.group(1);
      } else {
         // FIXME
         logger.error("No receivers found for: " + pathToJob);
         return oldExchange;
      }

      oldExchange.getIn().setHeader("to", receiverFinder.findReceiverForJob(jobUUID));
      
      return oldExchange;
   }

}