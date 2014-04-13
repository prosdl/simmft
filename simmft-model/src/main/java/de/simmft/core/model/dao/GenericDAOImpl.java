package de.simmft.core.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDAOImpl<T, I extends Serializable> implements GenericDAO<T, I>{

   private Class<T> type;

   @Autowired
   private SessionFactory sessionFactory;
   public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
   }
   protected SessionFactory getSessionFactory() {
       if (sessionFactory == null)
           throw new IllegalStateException("SessionFactory has not been set on DAO before usage");
       return sessionFactory;
   }

   public Class<T> getType() {
       return type;
   }

   @SuppressWarnings("unchecked")
   public GenericDAOImpl() {
       this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }
   
   public Session getSession() {
      return getSessionFactory().getCurrentSession();
   }

   @SuppressWarnings("unchecked")
   @Override
   public T find(I id) {
       return (T) getSession().get(getType(), id);
   }

   @Override
   public void delete(T obj) {
       getSession().delete(obj);
   }

   @Override
   public void saveOrUpdate(T obj) {
       getSession().saveOrUpdate(obj);
   }
}