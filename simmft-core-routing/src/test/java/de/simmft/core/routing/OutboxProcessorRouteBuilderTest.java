package de.simmft.core.routing;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-simmft-core-routing-test.xml" })
@ActiveProfiles("mock")
public class OutboxProcessorRouteBuilderTest {
   @Autowired
   private CamelContext camelContext;
   
   @Autowired
   private OutboxProcessorRouteBuilder outboxProcessorRouteBuilder;

   @Test
   public void testRoute() throws Exception {
      camelContext.addRoutes(outboxProcessorRouteBuilder);
      camelContext.start();
      Thread.sleep(5000);
      camelContext.stop();
   }

}
