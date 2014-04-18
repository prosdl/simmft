package de.simmft.core.model.dao;

import java.util.List;

import de.simmft.core.model.Job;
import de.simmft.core.model.MftAgent;

public interface JobDAO extends GenericDAO<Job, String> {

   List<Job> findAllSendJobsForMftAgent(MftAgent mftAgent);

   List<Job> findAllReceiveJobsForMftAgent(MftAgent mftAgent);

}
