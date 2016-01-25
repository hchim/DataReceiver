package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Market.Columns;
import org.mongodb.morphia.query.Query;

public class MarketDAO extends BaseDAO {

    @Override
    public Class getEntityClass() {
        return Market.class;
    }

    public Market findByName(String name) {
        Query<Market> query = datastore.createQuery(getEntityClass());
            query.field(Columns.NAME.val()).equal(name);
        Object object = findOne(query);
        return object != null ? (Market) object : null;
    }
}
