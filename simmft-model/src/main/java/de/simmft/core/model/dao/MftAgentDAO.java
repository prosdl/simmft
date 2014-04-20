package de.simmft.core.model.dao;

import de.simmft.core.model.MftAgent;

public interface MftAgentDAO extends GenericDAO<MftAgent, Long>{

   MftAgent findByClientId(String clientId);

}
