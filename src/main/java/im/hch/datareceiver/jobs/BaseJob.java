package im.hch.datareceiver.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

public abstract class BaseJob implements Job {
    static Logger logger = LogManager.getLogger();

    public static final String ARGS = "args";
    protected JobExecutionContext jobExecutionContext;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.jobExecutionContext = jobExecutionContext;

        try {
            SchedulerContext context = jobExecutionContext.getScheduler().getContext();
            Object[] args = (Object[]) context.get(ARGS);
            String result = execute(args);
            handleJobResult(result);
        } catch (SchedulerException se) {
            logger.error("Failed to get scheduler context", se);
        }
    }

    public abstract String execute(Object[] args);

    private void handleJobResult(String result) {
        //TODO handle job result
        logger.debug(result);
    }
}