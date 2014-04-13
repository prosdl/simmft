package de.simmft.core.model.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.model.Job;
import de.simmft.core.model.SendJob;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" }) 
@TransactionConfiguration(defaultRollback = false)
@Transactional

public class JobDAOTest {
   
   @Autowired
   private JobDAO jobDAO;

   @Test
   public void testUuidPKGenerator() {
      Job job = new SendJob();
      jobDAO.saveOrUpdate(job);
      Assert.assertNotNull(job.getUuid());
      System.out.println(job.getUuid());
   }
}
