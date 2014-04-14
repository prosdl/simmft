package de.simmft.rest.resources;

import java.util.List;

import de.simmft.core.model.Job;

public interface FooService {

   String hello();

   List<Job> getJobs();

}
