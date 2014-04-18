package de.simmft.core.services.jobs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.simmft.core.model.Job;
import de.simmft.core.model.MftAgent;
import de.simmft.core.model.dao.JobDAO;
import de.simmft.core.model.dao.MftAgentDAO;
import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.jobs.JobService;

@Service
public class JobServiceDefaultImpl implements JobService {

   @Autowired
   private JobDAO jobDAO;

   @Autowired
   private MftAgentDAO mftAgentDAO;

   @Override
   public List<Job> getSendJobsForMftAgent(String mftAgentName)
         throws MftCoreServiceException {
      MftAgent mftAgent = mftAgentDAO
            .findByPropertyUnique("name", mftAgentName);
      if (mftAgent == null) {
         throw new MftCoreServiceException("No agent found for '"
               + mftAgentName + "'");
      }
      return jobDAO.findAllSendJobsForMftAgent(mftAgent);
   }

   @Override
   public List<Job> getReceiveJobsForMftAgent(String mftAgentName)
         throws MftCoreServiceException {
      MftAgent mftAgent = mftAgentDAO
            .findByPropertyUnique("name", mftAgentName);
      if (mftAgent == null) {
         throw new MftCoreServiceException("No agent found for '"
               + mftAgentName + "'");
      }
      return jobDAO.findAllReceiveJobsForMftAgent(mftAgent);
   }

   @Override
   public List<Job> getSendOrReceiveJobsForMftAgent(String mftAgentName)
         throws MftCoreServiceException {
      MftAgent mftAgent = mftAgentDAO
            .findByPropertyUnique("name", mftAgentName);
      if (mftAgent == null) {
         throw new MftCoreServiceException("No agent found for '"
               + mftAgentName + "'");
      }
      List<Job> jobs = jobDAO.findAllSendJobsForMftAgent(mftAgent);
      jobs.addAll(jobDAO.findAllReceiveJobsForMftAgent(mftAgent));
      return jobs;
   }

}
