package de.simmft.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.simmft.core.model.Job;
import de.simmft.core.model.Link;

@Path("/hello")
public class HelloResource {

   @Autowired
   private FooService fooService;

   @GET
   @Produces("application/json")
   public String simpleStringResponse(@Context UriInfo uriinfo) throws JsonProcessingException {
      ObjectMapper om = new ObjectMapper();
      om.setSerializationInclusion(Include.NON_NULL);
      ObjectWriter ow = om.writerWithDefaultPrettyPrinter();
      List<Job> jobs = fooService.getJobs();
      for (Job j: jobs) {
         j.add(new Link(uriinfo.getAbsolutePath().toString()));
      }
      return ow.writeValueAsString(jobs);
   }
}