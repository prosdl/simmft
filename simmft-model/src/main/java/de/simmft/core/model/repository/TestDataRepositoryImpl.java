package de.simmft.core.model.repository;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.model.AdministrativeApplication;
import de.simmft.core.model.MftAgent;
import de.simmft.core.model.ReceiveJob;
import de.simmft.core.model.ReceiveJob.MultipleMatchingFilesPolicy;
import de.simmft.core.model.ReceiveJob.OnFilePresentPolicy;
import de.simmft.core.model.SendJob;
import de.simmft.core.model.SendJob.DeleteAfterTransferPolicy;
import de.simmft.core.model.dao.AdministrativeApplicationDAO;
import de.simmft.core.model.dao.DaoMap;
import de.simmft.core.model.dao.GenericDAO;
import de.simmft.core.model.dao.JobDAO;
import de.simmft.core.model.dao.MftAgentDAO;

@Repository
@Transactional
public class TestDataRepositoryImpl implements TestDataRepository {
   private static Logger logger = LoggerFactory
         .getLogger(TestDataRepositoryImpl.class);

   private static final String TEST_DEFAULT_RECEIVE_JOB_1 = "test:default-receive-job-1";

   private static final String TEST_DEFAULT_SEND_JOB_1 = "test:default-send-job-1";

   private static final String TEST_APPLICATION = "test-application";

   private static final String TEST_MFT_AGENT_RECEIVER_1 = "test.mft-agent-receiver-1";

   private static final String TEST_MFT_AGENT_SENDER_1 = "test.mft-agent-sender-1";

   protected class Precondition {

      public boolean contains(Class<?> entityClass, String name, Object value) {
         return daoMap.getDAOForEntity(entityClass).countAllWithProperty(name,
               value) > 0;
      }
   }

   @Autowired
   private DaoMap daoMap;

   @Autowired
   private JobDAO jobDAO;

   @Autowired
   private MftAgentDAO mftAgentDAO;

   @Autowired
   private AdministrativeApplicationDAO administrativeApplicationDAO;

   public static Object getFieldValue(String fieldName, Object obj) {
      try {
         Field f = findPrivateFieldIncludingSuperclass(obj, fieldName);
         f.setAccessible(true);
         return f.get(obj);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
   
   public static Field findPrivateFieldIncludingSuperclass(Object o, String name) {
      try {
         return o.getClass().getDeclaredField(name);
      } catch (NoSuchFieldException e) {
         try {
            return o.getClass().getSuperclass().getDeclaredField(name);
         } catch (Exception e1) {
            throw new RuntimeException(e);
         }
      } catch (SecurityException e) {
         throw new RuntimeException(e);
      }
   }

   public static void setFieldValue(String fieldName, Object val, Object obj) {
      try {
         Field f = findPrivateFieldIncludingSuperclass(obj, fieldName);
         f.setAccessible(true);
         f.set(obj, val);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public <T> T addOrUpdateEntity(T entity, String pkName, String property, Object value) {
      @SuppressWarnings("unchecked")
      GenericDAO<T, Serializable> dao = (GenericDAO<T, Serializable>) daoMap
            .getDAOForEntity(entity.getClass());
      T entityPersited = dao.findByPropertyUnique(property, value);
      Object pk = (entityPersited != null) ? getFieldValue(pkName, entityPersited)
            : null;
      setFieldValue(pkName, pk, entity);
      return dao.merge(entity);
   }

   @Override
   public void generate() {
      logger.info("Generate testdata");

      MftAgent mftAgentSender = addOrUpdateEntity(
            new MftAgent(TEST_MFT_AGENT_SENDER_1), "id", "name",
            TEST_MFT_AGENT_SENDER_1);
      MftAgent mftAgentReceiver = addOrUpdateEntity(new MftAgent(
            TEST_MFT_AGENT_RECEIVER_1), "id", "name", TEST_MFT_AGENT_RECEIVER_1);

      
      AdministrativeApplication testApp = addOrUpdateEntity(new AdministrativeApplication(
            TEST_APPLICATION),"id", "name", TEST_APPLICATION);

      SendJob sendjob = SendJob.Builder.create(TEST_DEFAULT_SEND_JOB_1)
            .from(mftAgentSender).to(mftAgentReceiver)
            .scheduledAt("0/30 * * * * ?")
            .afterTransfer(DeleteAfterTransferPolicy.DELETE_FILE)
            .forApplication(testApp).withSrcDir("/tmp")
            .withSrcFilename("foo.dat").build();
      
      addOrUpdateEntity(sendjob, "uuid", "name", TEST_DEFAULT_SEND_JOB_1);

      ReceiveJob receivejob = ReceiveJob.Builder.create(TEST_DEFAULT_RECEIVE_JOB_1)
            .from(mftAgentSender).to(mftAgentReceiver)
            .scheduledAt("0/30 * * * * ?")
            .whenFileIsLocallyPresent(OnFilePresentPolicy.REPLACE_FILE)
            .whenMultipleFilesInStore(MultipleMatchingFilesPolicy.GET_ALL)
            .referringTo(sendjob) 
            .withTargetDir("/tmp").withTargetFilename("bar.dat").build();
      
      addOrUpdateEntity(receivejob, "uuid", "name", TEST_DEFAULT_RECEIVE_JOB_1);
   }
}
