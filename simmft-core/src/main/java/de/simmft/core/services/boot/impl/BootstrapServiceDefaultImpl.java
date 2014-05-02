package de.simmft.core.services.boot.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.model.repository.CoreDataRepository;
import de.simmft.core.model.repository.TestDataRepository;
import de.simmft.core.routing.CamelBootstrap;
import de.simmft.core.services.boot.BootstrapService;
import de.simmft.core.tools.PrettyLog;

@Service
@Transactional
public class BootstrapServiceDefaultImpl implements BootstrapService {
   private Logger logger = LoggerFactory.getLogger(BootstrapServiceDefaultImpl.class);

   @Autowired
   private TestDataRepository testDataRepository;
   
   @Autowired
   private CoreDataRepository coreDataRepository;
   
   @Autowired
   private CamelBootstrap camelBootstrap;
   
   @Override
   public void setup() {
      logger.info(PrettyLog.boxedHeader("MFT bootstrapping ..."));
      coreDataRepository.generate();
      testDataRepository.generate();
      
      try {
         logger.info("Starting camel outbox routing ...");
         camelBootstrap.kickCamel();;
      } catch (Exception e) {
         logger.error("Problem starting camel ...",e);
      }
   }

}
