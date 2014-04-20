package de.simmft.core.model.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.simmft.core.model.MftAgent;

@Repository
public class MftAgentDAOImpl extends GenericDAOImpl<MftAgent, Long> implements MftAgentDAO {

   @Override
   public MftAgent findByClientId(String clientId) {
      return (MftAgent) getSession().createCriteria(MftAgent.class).
            createCriteria("oauthClientCredentials").
            add(Restrictions.eq("clientId", clientId)).uniqueResult();
   }

}
