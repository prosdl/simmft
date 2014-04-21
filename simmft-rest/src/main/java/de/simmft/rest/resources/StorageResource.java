package de.simmft.rest.resources;

import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.storage.StorageService;
import de.simmft.storage.api.FileInfo;

@Path("/storage")
public class StorageResource {
   private Logger logger = LoggerFactory.getLogger(StorageResource.class);
   
   @Autowired
   private StorageService storageService;

   @POST
   @Path("/{mft-agent}/outbox/{job-uri}")
   @Produces("application/json")
   public Response upload(@PathParam("mft-agent") String mftAgentId,
         @PathParam("job-uri") String jobUri, InputStream is) throws JsonProcessingException {

      logger.info("upload");
      FileInfo fileInfo = null;
      try {
         fileInfo = storageService.storeFile(is, mftAgentId, jobUri);
      } catch (MftCoreServiceException e) {
         logger.error("copy",e);
         return Response.serverError().entity(e.getMessage()).build();
      }
      ObjectMapper om = new ObjectMapper();
      om.setSerializationInclusion(Include.NON_NULL);
      ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

      return Response.ok().entity(ow.writeValueAsString(fileInfo)).build();
   }
}
