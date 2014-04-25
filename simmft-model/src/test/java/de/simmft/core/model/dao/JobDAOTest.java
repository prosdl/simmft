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
import de.simmft.core.model.MftAgent;
import de.simmft.core.model.ReceiveJob;
import de.simmft.core.model.SendJob;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-simmft-model-emedded-test.xml" }) 
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class JobDAOTest {
   
   @Autowired
   private JobDAO jobDAO;
   
   @Autowired
   private MftAgentDAO mftAgentDAO;

   @Test
   public void testUuidPKGenerator() {
      Job job = new SendJob("foo");
      jobDAO.saveOrUpdate(job);
      Assert.assertNotNull(job.getUuid());
      System.out.println(job.getUuid());
   }
   
   @Test
   public void testFindJobsForMftAgent() {
      MftAgent mftAgent1 = new MftAgent("agent1");
      MftAgent mftAgent2 = new MftAgent("agent2");
      
      mftAgentDAO.saveOrUpdate(mftAgent1);
      mftAgentDAO.saveOrUpdate(mftAgent2);
      
      Job job1 = SendJob.Builder.create("job1").from(mftAgent1).to(mftAgent2).build();
      Job job2 = ReceiveJob.Builder.create("job2").from(mftAgent1).to(mftAgent2).build();
      jobDAO.saveOrUpdate(job1);
      jobDAO.saveOrUpdate(job2);
      
      Assert.assertEquals(1, jobDAO.findAllSendJobsForMftAgent(mftAgent1).size());
      Assert.assertEquals(0, jobDAO.findAllSendJobsForMftAgent(mftAgent2).size());
      Assert.assertEquals(0, jobDAO.findAllReceiveJobsForMftAgent(mftAgent1).size());
      Assert.assertEquals(1, jobDAO.findAllReceiveJobsForMftAgent(mftAgent2).size());
   }

}
