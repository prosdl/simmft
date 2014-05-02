package de.simmft.storage.routing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.model.MftAgent;
import de.simmft.core.model.dao.JobDAO;
import de.simmft.core.routing.ReceiverFinder;

@Component
@Transactional
public class ReceiverFinderDefault implements ReceiverFinder {
   
   @Autowired
   private JobDAO jobDAO;

   @Override
   public List<String> findReceiverForJob(String jobUUID) {
      Set<MftAgent> receiver = jobDAO.findAllReceiverForJob(jobUUID);
      List<String> receiverNames = new ArrayList<>();
      for (MftAgent agent: receiver) {
         receiverNames.add(agent.getName());
      }
      return receiverNames;
   }

}
