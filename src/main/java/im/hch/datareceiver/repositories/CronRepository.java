package im.hch.datareceiver.repositories;

import im.hch.datareceiver.dao.CronJobDAO;
import im.hch.datareceiver.dao.CronLogDAO;
import im.hch.datareceiver.model.CronJob;
import im.hch.datareceiver.model.CronLog;
import org.bson.types.ObjectId;

import java.util.List;

public class CronRepository {
    private CronJobDAO cronJobDAO;
    private CronLogDAO cronLogDAO;

    public CronRepository() {
        cronJobDAO = new CronJobDAO();
        cronLogDAO = new CronLogDAO();
    }

    public List<CronJob> getAllActiveJobs() {
        return cronJobDAO.getAllActiveJobs();
    }

    public void addCronLog(String jobId, CronLog.State state, String log) {
        CronLog cronLog = new CronLog(new ObjectId(jobId), state, log);
        cronLogDAO.insert(cronLog);
    }
}
