package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.CronJob;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Queue;

public class CronJobDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return CronJob.class;
    }

    public List<CronJob> getAllActiveJobs() {
        Query<CronJob> query = datastore.createQuery(getEntityClass());
        query.field(CronJob.Columns.STATUS.val()).equal(true);
        return query.asList();
    }
}
