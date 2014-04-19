package de.simmft.rest.resources;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import de.simmft.core.model.OauthUser;
import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.user.AccountManagementService;

public class MftClientDetailsService implements ClientDetailsService{
   private static Logger logger = LoggerFactory.getLogger(MftClientDetailsService.class);
   
   @Autowired
   private AccountManagementService accountManagementService;

   @Override
   public ClientDetails loadClientByClientId(String clientId)
         throws ClientRegistrationException {
      OauthUser oauthUser;
      try {
         oauthUser = accountManagementService.findAuthorizedUser(clientId);
      } catch (MftCoreServiceException e) {
         logger.warn("while loadClientByClientId",e);
         throw new ClientRegistrationException("error while searching for '" + clientId + "'",e);
      }

      
      BaseClientDetails clientDetails = new BaseClientDetails();
      clientDetails.setAccessTokenValiditySeconds(3600*8);
      clientDetails.setAuthorities(oauthUser.getRole().getPermissions());
      clientDetails.setClientId(clientId);
      clientDetails.setClientSecret(oauthUser.getOauthClientCredentials().getHashedClientPassword());
      clientDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials"));;
      return clientDetails;
   }

}
