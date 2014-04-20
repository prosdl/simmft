package de.simmft.core.model.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.simmft.core.model.User;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, String> implements UserDAO{
   
   @Override
   public User findByClientId(String clientId) {
      return (User) getSession().createCriteria(User.class).
            createCriteria("oauthClientCredentials").
            add(Restrictions.eq("clientId", clientId)).uniqueResult();
   }
}
