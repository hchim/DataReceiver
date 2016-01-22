package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.SymbolPrice;

public class SymbolPriceDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return SymbolPrice.class;
    }
}
