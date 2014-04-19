package de.simmft.rest.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.simmft.core.model.FileMetaInfo;

@Path("/storage")
public class StorageResource {
   private Logger logger = LoggerFactory.getLogger(StorageResource.class);

   @POST
   @Path("/{mft-agent}/outbox/{job-uri}")
   @Produces("application/json")
   public Response upload(@PathParam("mft-agent") String mftAgentId,
         @PathParam("job-uri") String jobUri, InputStream is) throws JsonProcessingException {

      logger.info("upload");
      try {
         Files.copy(is, new File("/tmp/" + jobUri).toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
         logger.error("copy",e);
         return Response.serverError().entity(e.getMessage()).build();
      }
      FileMetaInfo info = new FileMetaInfo();
      info.setCreated(new Date());
      info.setUuid("xxxxxxxxxxxxxxxxxxxxx");
      ObjectMapper om = new ObjectMapper();
      om.setSerializationInclusion(Include.NON_NULL);
      ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

      return Response.ok().entity(ow.writeValueAsString(info)).build();
   }
}