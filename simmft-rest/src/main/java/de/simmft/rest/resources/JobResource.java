package de.simmft.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.simmft.core.model.Job;
import de.simmft.core.model.Link;
import de.simmft.core.services.exception.MftCoreServiceException;
import de.simmft.core.services.jobs.JobService;

@Path("/jobs")
public class JobResource {
   private Logger logger = LoggerFactory.getLogger(StorageResource.class);

   @Autowired
   private JobService jobService;
   
   @GET
   @Produces("application/json")
   public Response getJobs(@Context UriInfo uriinfo, @QueryParam("mft-agent") String mftAgentName) throws JsonProcessingException {
      
      List<Job> jobs;
      try {
         jobs = jobService.getSendOrReceiveJobsForMftAgent(mftAgentName);
      } catch (MftCoreServiceException e) {
         logger.error("getSendOrReceiveJobsForMftAgent",e);
         return Response.serverError().entity(e.getMessage()).build();
      }
      
      for (Job job: jobs) {
         job.add(new Link(uriinfo.getAbsolutePath().toString() + "/" + job.getUuid()));
      }
      
      ObjectMapper om = new ObjectMapper();
      om.setSerializationInclusion(Include.NON_NULL)
        .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

      return Response.ok().entity(ow.writeValueAsString(jobs)).build();
    
   }
}
