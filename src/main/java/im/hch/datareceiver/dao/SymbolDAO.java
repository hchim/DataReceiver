package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Symbol;

public class SymbolDAO extends BaseDAO {
    @Override
    public Class getEntityClass() {
        return Symbol.class;
    }
}
