package im.hch.datareceiver.repositories;

import im.hch.datareceiver.dao.MarketDAO;
import im.hch.datareceiver.dao.SymbolDAO;
import im.hch.datareceiver.dao.SymbolPriceDAO;
import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StockRepository {
    private SymbolDAO symbolDAO;
    private SymbolPriceDAO symbolPriceDAO;
    private MarketDAO marketDAO;

    public StockRepository() {
        symbolDAO = new SymbolDAO();
        symbolPriceDAO = new SymbolPriceDAO();
        marketDAO = new MarketDAO();
    }

    public List<Symbol> getSymbolsOfMarket(String marketName) {
        List<Symbol> symbols = new ArrayList<Symbol>();
        Market market = marketDAO.findByName(marketName);
        if (market == null) {
            return symbols;
        }

        return symbolDAO.findByMarket(market);
    }

    public void saveSymbolPrices(List<SymbolPrice> prices) {
        symbolPriceDAO.insert(prices);
    }

    public Calendar getLastUpdateTime(Symbol symbol, SymbolPrice.PriceType type) {
        SymbolPrice price = symbolPriceDAO.findLastUpdate(symbol, type);
        if (price != null) {
            Calendar time = Calendar.getInstance();
            time.setTime(price.getTime());
            return time;
        }

        return null;
    }
}
