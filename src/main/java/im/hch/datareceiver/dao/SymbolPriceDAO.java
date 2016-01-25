package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import im.hch.datareceiver.model.SymbolPrice.Columns;
import org.mongodb.morphia.query.Query;

public class SymbolPriceDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return SymbolPrice.class;
    }

    public SymbolPrice findLastUpdate(Symbol symbol, SymbolPrice.PriceType type) {
        Query<SymbolPrice> query = datastore.createQuery(getEntityClass());
        query.field(Columns.SYMBOL.val()).equal(symbol);
        query.order("-" + Columns.TIME.val());
        Object object = findOne(query);
        return object != null ? (SymbolPrice) object : null;
    }
}
