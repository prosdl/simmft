package de.simmft.rest.resources;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

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
   @Path("/{mft-agent}/outbox/{job-uuid}")
   @Produces("application/json")
   public Response upload(@PathParam("mft-agent") String mftAgentId,
         @PathParam("job-uuid") String jobUuid, InputStream is)
         throws JsonProcessingException {

      logger.info("upload");
      FileInfo fileInfo = null;
      try {
         fileInfo = storageService.storeFile(is, mftAgentId, jobUuid);
      } catch (MftCoreServiceException e) {
         logger.error("copy", e);
         return Response.serverError().entity(e.getMessage()).build();
      }
      ObjectMapper om = new ObjectMapper();
      om.setSerializationInclusion(Include.NON_NULL);
      ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

      return Response.ok().entity(ow.writeValueAsString(fileInfo)).build();
   }

   @GET
   @Path("/{mft-agent}/inbox/{job-uuid}/{filename}")
   @Produces(MediaType.APPLICATION_OCTET_STREAM)
   public Response download(@PathParam("mft-agent") String mftAgentId,
         @PathParam("job-uuid") String jobUuid,
         @PathParam("filename") String filename) {
      
      StreamingOutput stream;
      try {
         stream = storageService.readFile(mftAgentId, jobUuid, filename);
      } catch (MftCoreServiceException e) {
         logger.error("copy", e);
         return Response.serverError().entity(e.getMessage()).build();
      }

      return Response.ok().entity(stream)
            .header("content-disposition","attachment; filename = " + filename)
            .build();
   }
}
