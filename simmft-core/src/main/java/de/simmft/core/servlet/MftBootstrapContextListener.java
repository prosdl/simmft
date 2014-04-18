package de.simmft.core.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import de.simmft.core.services.boot.BootstrapService;


public class MftBootstrapContextListener  implements ServletContextListener {
   
   @Autowired
   private BootstrapService bootstrapService;

   @Override
   public void contextDestroyed(ServletContextEvent arg0) {
   }

   @Override
   public void contextInitialized(ServletContextEvent arg0) {
      SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
      bootstrapService.setup();
   }

}
