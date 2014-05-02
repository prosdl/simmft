package de.simmft.core.routing;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("outboxProcessorRouteBuilder")
public class OutboxProcessorRouteBuilder extends RouteBuilder {
   private static final Logger logger = LoggerFactory
         .getLogger(OutboxProcessorRouteBuilder.class);

   @Autowired
   private OutboxListProducer outboxListProducer;
   
   @Autowired
   private EnrichWithReceiverAggregationStrategy enrichWithReceiverAggregationStrategy;
   
   // FIXME
   private int timerInMilliseconds = 5000;


   @Override
   public void configure() throws Exception {
      logger.info("configuring route ...");
//      onException(Exception.class).maximumRedeliveries(0).to("log:mft-routing?level=error&showStackTrace=true");
      from("direct:foo").setBody(constant(null));
      from("timer://simple?period=" + timerInMilliseconds)
            .process(new Processor() {
               @Override
               public void process(Exchange exchange) throws Exception {
                  logger.info("Route triggered.");
                  exchange.getOut().setBody(
                        outboxListProducer.produceOutboxList());
               }
            }).split(body())
            .parallelProcessing()
            .log("outbox: ${body}")
            .enrich("direct:foo", enrichWithReceiverAggregationStrategy)
            .log("${body} -----> ${header.to}")
            .to("bean:fileMover");
   }

}
