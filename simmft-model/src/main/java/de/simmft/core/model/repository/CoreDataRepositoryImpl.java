package de.simmft.core.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.model.Permission.PermissionEnum;
import de.simmft.core.model.Permission;
import de.simmft.core.model.Role;
import de.simmft.core.model.dao.PermissionDAO;
import de.simmft.core.model.dao.RoleDAO;
import de.simmft.core.model.dao.UserDAO;

@Repository
@Transactional
public class CoreDataRepositoryImpl implements CoreDataRepository {

   @Autowired
   private UserDAO userDAO;
   
   @Autowired 
   private RoleDAO roleDAO;
   
   @Autowired
   private PermissionDAO permissionDAO;
   
   @Override
   public void generate() {
      
      for (PermissionEnum type: PermissionEnum.values()) {
         permissionDAO.saveOrUpdate(new Permission(type));
      }
      
      Role roleMftAgent = new Role("mftagent", PermissionEnum.MFT_GET_JOB_LIST);
      roleDAO.saveOrUpdate(roleMftAgent);
      
   }

}
