package de.simmft.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IJobResource {

   Response getJobs(HttpServletRequest httpRequest, UriInfo uriinfo,
         String mftAgentName) throws JsonProcessingException;

}
