package com.datacert.portal.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class PortalScheduler {

	private static Scheduler scheduler;
	private int batchSize = 20;
	private int batchIntervalInMin = 1;

	public PortalScheduler(int batchSize, int batchIntervalInMin) {
		this.batchIntervalInMin = batchIntervalInMin;
		this.batchSize = batchSize;
	}

	public void startScheduler() throws SchedulerException, InterruptedException {

		if (!scheduler.isStarted()) {
			JobDetail job = JobBuilder.newJob(PortalJob.class).withIdentity("submitSurveyJob", "submitSurveyJobGroup")
					.usingJobData("batchSize", batchSize).build();

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("submitSurveyTrigger", "submitSurveyTriggerGroup")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(batchIntervalInMin).repeatForever()).build();

			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
		}
	}

	// Stop all thread before shutting down
	public void stopScheduler() throws SchedulerException {
		if (scheduler.isStarted()) {
			scheduler.shutdown(true);
		}
	}
}
