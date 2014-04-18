package de.simmft.core.model.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, I extends Serializable> {
   public T find(I id);
   public void delete(T obj);
   public void saveOrUpdate(T obj);
   Integer countAllWithProperty(String propertyName, Object value);
   Integer countAll();
   Class<T> getType();
   List<T> findAll();
   List<T> findByProperty(String propertyName, Object value);
   T findByPropertyUnique(String propertyName, Object value);
   T merge(T obj);
}