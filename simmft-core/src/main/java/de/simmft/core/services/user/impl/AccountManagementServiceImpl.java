package de.simmft.core.services.user.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.simmft.core.model.MftAgent;
import de.simmft.core.model.OauthUser;
import de.simmft.core.model.User;
import de.simmft.core.model.dao.MftAgentDAO;
import de.simmft.core.model.dao.UserDAO;
import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.user.AccountManagementService;

@Service
@Transactional
public class AccountManagementServiceImpl implements AccountManagementService {
   
   @Autowired
   private MftAgentDAO mftAgentDAO;
   
   @Autowired
   private UserDAO userDAO;

   @Override
   public OauthUser findAuthorizedUser(String clientId) throws MftCoreServiceException {
      MftAgent mftAgent = mftAgentDAO.findByClientId(clientId);
      if (mftAgent != null) return mftAgent;
      
      User user = userDAO.findByClientId(clientId);
      if (user != null) return user;
      
      throw new MftCoreServiceException("user '" + clientId + "' not found.");
   }

}
