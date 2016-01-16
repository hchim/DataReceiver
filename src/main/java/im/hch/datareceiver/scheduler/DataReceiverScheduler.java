package im.hch.datareceiver.scheduler;

import im.hch.datareceiver.Config;
import im.hch.datareceiver.jobs.BaseJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class DataReceiverScheduler {

    private static final DataReceiverScheduler instance = new DataReceiverScheduler();
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    private static final Logger logger = Logger.getLogger(DataReceiverScheduler.class);
    private static final String GROUP_NAME = "DR_GROUP";

    public static DataReceiverScheduler getInstance() {
        return instance;
    }

    private DataReceiverScheduler() {
        schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException se) {
            logger.fatal("Failed to get scheduler, quit.", se);
            System.exit(-1);
        }
    }

    public boolean addJob(String name, String className, String cronExpression, Object[] args) {
        try {
            Class jobClass = Class.forName(className);
            if (!Job.class.isAssignableFrom(jobClass)) {
                logger.error(String.format("Class %s does not implements interface Job. Failed to add job.", className));
                return false;
            }
            long time = System.currentTimeMillis();
            JobDetail job = JobBuilder.newJob(jobClass)
                    .withIdentity(name + "-" + time, GROUP_NAME)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("Trigger-" + time, GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.getContext().put(BaseJob.ARGS, args);
            scheduler.scheduleJob(job, trigger);
            return true;
        } catch (ClassNotFoundException ex) {
            logger.error(String.format("Class %s cannot be found, failed to create job.", className), ex);
            return false;
        } catch (SchedulerException se) {
            logger.error("Failed to schedule job.", se);
            return false;
        }
    }

    public boolean addJob(Config.JobConfig config) {
        return addJob(config.name, config.className, config.cronExpression, config.args);
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.fatal("Failed to start scheduler.");
            System.exit(-1);
        }
    }
}
