package com.v2.booksys.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import com.v2.booksys.quartz.jobs.AmazonDataRetrievalJob;

public class QuartzListener implements ServletContextListener {
	org.slf4j.Logger logger = LoggerFactory.getLogger(QuartzListener.class);
    Scheduler scheduler = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
    	logger.info("Quartz Context Initialized");
            
            try {
            		PropertyConfigurator.configure("log4j_Jobs.xml");
            	
            		// Setup the Job class and the Job group
                    JobDetail job = newJob(AmazonDataRetrievalJob.class).withIdentity(
                                    "CronQuartzJob", "Group").build();
                    try {
						String exp = FileUtils.readFileToString(new File("JobTime.txt"));
						// Create a Trigger that fires every 5 minutes.
	                    Trigger trigger = newTrigger()
	                    .withIdentity("TriggerName", "Group")
	                    .withSchedule(CronScheduleBuilder.cronSchedule(exp))
	                    .build();

	                    // Setup the Job and Trigger with Scheduler & schedule jobs
	                    scheduler = new StdSchedulerFactory().getScheduler();
	                    scheduler.start();
	                    scheduler.scheduleJob(job, trigger);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
            }
            catch (SchedulerException e) {
                    e.printStackTrace();
            }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
            System.out.println("Context Destroyed");
            try 
            {
                    scheduler.shutdown();
            } 
            catch (SchedulerException e) 
            {
                    e.printStackTrace();
            }
    }
}