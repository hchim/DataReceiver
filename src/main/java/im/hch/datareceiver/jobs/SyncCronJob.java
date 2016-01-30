package im.hch.datareceiver.jobs;

import im.hch.datareceiver.model.CronJob;
import im.hch.datareceiver.repositories.CronRepository;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * This is the default task that runs with the scheduler.
 * It periodically sync the active tasks with database.
 */
public class SyncCronJob extends BaseJob {
    private static final Logger logger = Logger.getLogger(SyncCronJob.class);
    public static final String JOB_GROUP_NAME = "DB_JOB_GROUP";
    private Scheduler scheduler;

    @Override
    public String execute(Object[] args) {
        CronRepository cronRepository = new CronRepository();
        List<CronJob> jobs = cronRepository.getAllActiveJobs();

        scheduler = jobExecutionContext.getScheduler();
        HashMap<String, JobKey> jobKeyHashMap = getJobKeys();
        // schedule new jobs and remove job keys that are still active from HashMap
        int addedJobs = scheduleNewJobs(jobKeyHashMap, jobs);
        int deletedJobs = removeDisabledJobs(jobKeyHashMap);

        return String.format("SyncCronJob - job scheduled: %d ; job removed: %d", addedJobs, deletedJobs);
    }

    private HashMap<String, JobKey> getJobKeys() {
        HashMap<String, JobKey> jobKeyHashMap = new HashMap<String, JobKey>();

        try {
            for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(JOB_GROUP_NAME))) {
                jobKeyHashMap.put(jobKey.getName(), jobKey);
            }

            return jobKeyHashMap;
        } catch (SchedulerException ex) {
            logger.warn("Failed to get job keys.", ex);
            return jobKeyHashMap;
        }
    }

    private int scheduleNewJobs(HashMap<String, JobKey> jobKeyHashMap, List<CronJob> jobs) {
        int addedJobs = 0;

        // schedule new jobs
        String jobName;
        for (CronJob job : jobs) {
            jobName = job.getId().toHexString(); // use id as job name
            if (jobKeyHashMap.containsKey(jobName)) { // job is still active
                jobKeyHashMap.remove(jobName);
            } else { // new job
                scheduleJob(job);
                addedJobs++;
            }
        }

        return addedJobs;
    }

    private int removeDisabledJobs(HashMap<String, JobKey> jobKeyHashMap) {
        for (JobKey jobKey : jobKeyHashMap.values()) {
            try {
                scheduler.deleteJob(jobKey);
            } catch (SchedulerException e) {
                logger.warn("Failed to delete Job: " + jobKey.getName(), e);
            }
        }

        return jobKeyHashMap.size();
    }

    private void scheduleJob(CronJob job) {
        try {
            Class jobClass = Class.forName(job.getClassName());
            if (!Job.class.isAssignableFrom(jobClass)) {
                logger.error(String.format("Class %s does not implements interface Job. Failed to add job.",
                        job.getClassName()));
                return;
            }
            String jobName = job.getId().toHexString();
            TimeZone timeZone = TimeZone.getDefault();
            if (job.getTimezone() != null) {
                timeZone = TimeZone.getTimeZone(job.getTimezone());
            }

            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, JOB_GROUP_NAME)
                    .build();
            jobDetail.getJobDataMap().put(BaseJob.ARGS, job.getArgs());

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("Trigger-" + jobName, JOB_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpr()).inTimeZone(timeZone))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException ex) {
            logger.error(String.format("Class %s cannot be found, failed to create job.", job.getClassName()), ex);
        } catch (SchedulerException se) {
            logger.error("Failed to schedule job.", se);
        }
    }
}
