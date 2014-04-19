package de.simmft.core.services.user;

import de.simmft.core.model.OauthUser;
import de.simmft.core.services.exception.MftCoreServiceException;

public interface AccountManagementService {
   public OauthUser findAuthorizedUser(String username) throws MftCoreServiceException;
}
