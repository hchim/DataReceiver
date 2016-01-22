package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;

import java.util.List;

public class MarketDAO extends BaseDAO {

    @Override
    public Class getEntityClass() {
        return Market.class;
    }
}
