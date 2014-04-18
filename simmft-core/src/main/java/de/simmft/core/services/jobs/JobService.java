package de.simmft.core.services.jobs;

import java.util.List;

import de.simmft.core.model.Job;
import de.simmft.core.model.MftAgent;
import de.simmft.core.services.exception.MftCoreServiceException;

public interface JobService {
   public List<Job> getSendJobsForMftAgent(String mftAgentName) throws MftCoreServiceException;
   public List<Job> getReceiveJobsForMftAgent(String mftAgentName) throws MftCoreServiceException;
   public List<Job> getSendOrReceiveJobsForMftAgent(String mftAgentName) throws MftCoreServiceException;
}
