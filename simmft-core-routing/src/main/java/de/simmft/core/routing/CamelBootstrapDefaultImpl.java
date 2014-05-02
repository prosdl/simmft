package de.simmft.core.routing;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CamelBootstrapDefaultImpl implements CamelBootstrap {
   @Autowired
   private CamelContext camelContext;
   

   @Autowired
   @Qualifier("outboxProcessorRouteBuilder")
   private RouteBuilder outboxProcessorRouteBuilder;
   
   @Override
   public void kickCamel() throws Exception {
      camelContext.addRoutes(outboxProcessorRouteBuilder);
      camelContext.start();
   }

}
