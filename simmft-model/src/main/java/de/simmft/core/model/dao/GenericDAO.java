package de.simmft.core.model.dao;

import java.io.Serializable;

public interface GenericDAO<T, I extends Serializable> {
   public T find(I id);
   public void delete(T obj);
   public void saveOrUpdate(T obj);
}