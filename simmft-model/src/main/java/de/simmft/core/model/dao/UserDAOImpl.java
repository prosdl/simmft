package de.simmft.core.model.dao;

import org.springframework.stereotype.Repository;

import de.simmft.core.model.User;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, String> implements UserDAO{

}
