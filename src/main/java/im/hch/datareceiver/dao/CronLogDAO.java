package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.CronJob;

public class CronLogDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return CronJob.class;
    }
}
