package im.hch.datareceiver.jobs;

import im.hch.datareceiver.model.CronLog;
import im.hch.datareceiver.repositories.CronRepository;
import org.apache.log4j.Logger;
import org.quartz.*;

public abstract class BaseJob implements Job {
    static Logger logger = Logger.getLogger(BaseJob.class);

    protected CronRepository cronRepository;
    protected JobExecutionContext jobExecutionContext;
    private StringBuffer log;
    protected CronLog.State state = CronLog.State.UNKNOWN;

    public static final String ARGS = "args";
    private static final int MAX_EXEC_RESULT_LEN = 1024;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.jobExecutionContext = jobExecutionContext;
        this.cronRepository = new CronRepository();
        this.log = new StringBuffer();

        String jobName = "Unknown", groupName = "Unknown";
        try {
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            jobName = jobDetail.getKey().getName();
            groupName = jobDetail.getKey().getGroup();
            SchedulerContext context = jobExecutionContext.getScheduler().getContext();
            Object[] args = (Object[]) context.get(ARGS);
            String result = execute(args);
            handleJobResult(result);
        } catch (SchedulerException se) {
            logger.warn("Failed to get scheduler context", se);
            appendExecLog("Failed to get scheduler context. " + se.getMessage());
            state = CronLog.State.FAIL;
        } finally {
            // DB jobs were created by SyncCronJob and were assigned to this group
            if (SyncCronJob.JOB_GROUP_NAME.equals(groupName)) {
                cronRepository.addCronLog(jobName, state, log.toString());
            }
        }
    }

    /**
     * Execute the command and return execution result. If the execution fails,
     * it should returns null.
     * @param args args of the job
     * @return null if failed to execute, otherwise returns execution result
     */
    public abstract String execute(Object[] args);

    private void handleJobResult(String result) {
        if (result == null) {
            state = CronLog.State.FAIL;
            return;
        }

        state = CronLog.State.SUCCESS;
        if (result.length() > MAX_EXEC_RESULT_LEN) {
            result = result.substring(0, MAX_EXEC_RESULT_LEN);
        }
        appendExecLog(result);
    }

    protected void appendExecLog(String logstr) {
        log.append(logstr).append("\r\n");
    }
}
