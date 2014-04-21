package de.simmft.core.services.storage;

import java.io.InputStream;

import org.springframework.security.access.prepost.PreAuthorize;

import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.storage.api.FileInfo;

public interface StorageService {
   @PreAuthorize("hasRole('mft:storage:write')")
   FileInfo storeFile(InputStream is, String mftAgentId, String jobUri)
         throws MftCoreServiceException;

}
