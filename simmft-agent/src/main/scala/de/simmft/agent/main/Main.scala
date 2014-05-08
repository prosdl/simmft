package de.simmft.agent.main

import scala.concurrent.duration._
import org.quartz.SchedulerFactory
import org.quartz.impl.StdScheduler
import org.quartz.impl.StdSchedulerFactory
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.SimpleScheduleBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext


class HelloJob extends Job {
  override 
  def execute(ctx: JobExecutionContext) {
    println("Hello")
  }
}

object Main {

  def main(args: Array[String]) {

    val factory = new StdSchedulerFactory()
    val scheduler = factory.getScheduler
    scheduler.start

    val job = JobBuilder.newJob(classOf[HelloJob]).withIdentity("foo").build()
    val trigger = TriggerBuilder.newTrigger().withIdentity("foo-trigger").
                  withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()).startNow().build()
    scheduler.scheduleJob(job, trigger)
  }
}