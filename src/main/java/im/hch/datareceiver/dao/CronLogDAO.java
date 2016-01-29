package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.CronLog;

public class CronLogDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return CronLog.class;
    }
}
