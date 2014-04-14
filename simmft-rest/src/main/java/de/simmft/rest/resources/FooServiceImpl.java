package de.simmft.rest.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import de.simmft.core.model.Job;
import de.simmft.core.model.MftAgent;
import de.simmft.core.model.SendJob;
import de.simmft.core.model.SendJob.DeleteAfterTransferPolicy;

@Service
public class FooServiceImpl implements FooService {

   @Override
   public String hello() {
      return "Hello from " + getClass().getSimpleName();
   }
   
   @Override
   public List<Job> getJobs() {
      List<Job> jobs = new ArrayList<>();
      MftAgent from = new MftAgent();
      from.setId(4712l);
      from.setName("mft-agent-testsrv.lin64");
      from.setDescription("Test Sender");
      MftAgent to = new MftAgent();
      to.setId(333l);
      to.setName("mft-agent-localhost-devel");
      to.setDescription("Test Receiver");
      SendJob job = new SendJob();
      jobs.add(job);
      job.setCreated(new Date());
      job.setCronExpression("0/20 * * * * ?");
      job.setDescription("Testjob");
      job.setFrom(from);
      job.setTo(new HashSet<MftAgent>());
      job.getTo().add(to);
      job.setUseLegacyPGLocking(false);
      job.setDeleteAfterTransferPolicy(DeleteAfterTransferPolicy.DELETE_FILE);
      job.setSrcDir("/home/foo");
      job.setSrcFilename("bar.dat");
      return jobs;
   }
}
