package de.simmft.core.services.storage.impl;

import java.io.InputStream;

import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.storage.StorageService;
import de.simmft.storage.api.FileInfo;
import de.simmft.storage.api.StorageException;
import de.simmft.storage.api.StorageManager;

@Service
@Transactional
public class StorageServiceDefaultImpl implements StorageService {
   @Autowired
   private StorageManager storageManager;
   
   @Override
   public FileInfo storeFile(InputStream is, String mftAgentId, String jobUri) throws MftCoreServiceException {
      try {
         return storageManager.storeFile(is, mftAgentId, jobUri);
      } catch (StorageException e) {
         throw new MftCoreServiceException(e);
      }
   }
   
   @Override
   public StreamingOutput readFile(String mftAgentId, String jobUuid, String filename) throws MftCoreServiceException {
      try {
         return storageManager.readFile(mftAgentId, jobUuid, filename);
      } catch (StorageException e) {
         throw new MftCoreServiceException(e);
      }
   }
}
