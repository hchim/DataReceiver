package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.Symbol.Columns;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class SymbolDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return Symbol.class;
    }

    public List<Symbol> findByMarket(Market market) {
        Query<Symbol> query = datastore.createQuery(getEntityClass());
        query.field(Columns.MARKET.val()).equal(market);
        return query.asList();
    }
}
