package de.simmft.core.model.dao;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoMapImpl implements DaoMap {
   @SuppressWarnings("rawtypes")
   @Autowired
   private Map<String, GenericDAO> daos;
   
   @Override
   @SuppressWarnings("unchecked")
   public <T> GenericDAO<T, Serializable> getDAOForEntity(Class<T> entityClass) {
      System.out.println(daos);
      for (GenericDAO<?, Serializable> dao: daos.values()) {
         if (dao.getType().isAssignableFrom(entityClass)) {
            return (GenericDAO<T, Serializable>) dao;
         }
      }
      throw new IllegalArgumentException("No DAO found for: " + entityClass.getSimpleName());
   }
}
