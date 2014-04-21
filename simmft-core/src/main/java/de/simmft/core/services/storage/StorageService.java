package de.simmft.core.services.storage;

import java.io.InputStream;

import javax.ws.rs.core.StreamingOutput;

import org.springframework.security.access.prepost.PreAuthorize;

import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.storage.api.FileInfo;

public interface StorageService {
   @PreAuthorize("hasRole('mft:storage:write')")
   FileInfo storeFile(InputStream is, String mftAgentId, String jobUri)
         throws MftCoreServiceException;

   @PreAuthorize("hasRole('mft:storage:read')")
   StreamingOutput readFile(String mftAgentId, String jobUuid, String filename)
         throws MftCoreServiceException;

}
