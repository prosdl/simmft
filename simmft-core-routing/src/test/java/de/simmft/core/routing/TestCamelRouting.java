package de.simmft.core.routing;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-simmft-core-routing-test.xml" })
public class TestCamelRouting {
   private static final Logger logger = LoggerFactory
         .getLogger(TestCamelRouting.class);

   @Autowired
   private CamelContext camelContext;

   @Before
   public void before() throws Exception {
      logger.info("resetting camel ..." + camelContext);
      camelContext.stop();
      for (Route r : camelContext.getRoutes()) {
         camelContext.removeRoute(r.getId());
      }
      camelContext.removeEndpoints("direct://start");
   }

   @Test
   public void testInjectionAndSpringConfig() {
      Assert.assertNotNull(camelContext);
   }

   @Test
   public void testBeanComponent() throws Exception {
      camelContext.addRoutes(new RouteBuilder() {

         @Override
         public void configure() throws Exception {
            from("direct:start").beanRef("simpleBeanImpl", "echo").to(
                  "mock:results");
         }
      });
      MockEndpoint resultEndpoint = camelContext.getEndpoint("mock:results",
            MockEndpoint.class);
      resultEndpoint.expectedMessageCount(1);
      resultEndpoint.setAssertPeriod(300);
      camelContext.start();
      ProducerTemplate template = camelContext.createProducerTemplate();
      template.sendBody("direct:start", new Object());
      resultEndpoint.assertIsSatisfied();
      camelContext.stop();

      logger.info(resultEndpoint.getReceivedExchanges().toString());

   }

   private static class EnrichWithReceiversAggregationStrategy implements
         AggregationStrategy {

      @Override
      public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
         String originalBody = (String) oldExchange.getIn().getBody();

         if (originalBody.startsWith("mft-agent-sender-001")) {
            oldExchange.getIn().setHeader("to", "mft-agent-receiver-555");
         } else if (originalBody.startsWith("mft-agent-sender-002")) {
            oldExchange.getIn().setHeader("to",
                  "mft-agent-receiver-666,mft-agent-receiver-667");
         }
         return oldExchange;
      }

   }

   @Test
   public void testMft() throws Exception {
      ProducerTemplate template = camelContext.createProducerTemplate();
      final AggregationStrategy enrichAggregationStrategy = new EnrichWithReceiversAggregationStrategy();

      camelContext.addRoutes(new RouteBuilder() {
         @Override
         public void configure() throws Exception {
            from("direct:foo").setBody(constant(null));
            from("timer://myTimer?period=500")
                  .process(new Processor() {
                     @Override
                     public void process(Exchange exchange) throws Exception {
                        logger.info("\n" + 
                              "-------------------------------------------------\n" + 
                              "process()\n" + 
                              "-------------------------------------------------\n" 
                              );
                        List<String> outboxes = Arrays.asList(
                              "mft-agent-sender-001/outbox/" + UUID.randomUUID(),
                              "mft-agent-sender-002/outbox/" + UUID.randomUUID());
                        exchange.getOut().setBody(outboxes);
                     }
                  })
                  .split(body()).parallelProcessing()
                  .log("outbox: ${body}")
                  .enrich("direct:foo", enrichAggregationStrategy)
                  .log("${body} -----> ${header.to}").to("bean:fileMoverMock");
         }
      });
      MockEndpoint resultEndpoint = camelContext.getEndpoint("mock:results",
            MockEndpoint.class);

      camelContext.start();
      Thread.sleep(5000);
      camelContext.stop();
      logger.info(resultEndpoint.getReceivedExchanges().toString());
   }
}
