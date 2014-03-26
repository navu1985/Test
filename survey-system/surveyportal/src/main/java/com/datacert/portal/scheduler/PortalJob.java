package com.datacert.portal.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Async;

public class PortalJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
  }

  @Async
  public void sendSurveyToPassport(long userId) {
  }
}
