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
   public OauthUser findAuthorizedUser(String username) throws MftCoreServiceException {
      // FIXME find agent.clientd_id
      MftAgent mftAgent = mftAgentDAO.findByPropertyUnique("name", username);
      if (mftAgent != null) return mftAgent;
      
      // FIXME find user.clientd_id
      User user = userDAO.find(username);
      if (user != null) return user;
      
      throw new MftCoreServiceException("user '" + username + "' not found.");
   }

}
