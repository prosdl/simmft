package de.simmft.core.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.simmft.core.model.Job;
import de.simmft.core.model.MftAgent;
import de.simmft.core.model.ReceiveJob;
import de.simmft.core.model.SendJob;

@Repository
public class JobDAOImpl extends GenericDAOImpl<Job, String> implements JobDAO {
   @Override
   @SuppressWarnings("unchecked")
   public List<Job> findAllSendJobsForMftAgent(MftAgent mftAgent) {
      return getSession().createCriteria(SendJob.class).
            createCriteria("from").
            add(Restrictions.idEq(mftAgent.getId())).list();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<Job> findAllReceiveJobsForMftAgent(MftAgent mftAgent) {
      return getSession().createCriteria(ReceiveJob.class).
            createAlias("to","t").
            add(Restrictions.eq("t.id",mftAgent.getId())).list();
   }

   @Override
   public Set<MftAgent> findAllReceiverForJob(String jobUUID) {
      Job job = find(jobUUID);
      return job.getTo();
   }

}
