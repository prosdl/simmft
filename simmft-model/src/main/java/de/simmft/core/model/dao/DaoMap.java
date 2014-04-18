package de.simmft.core.model.dao;

import java.io.Serializable;

public interface DaoMap {

   public <T> GenericDAO<T, Serializable> getDAOForEntity(Class<T> entityClass);

}
