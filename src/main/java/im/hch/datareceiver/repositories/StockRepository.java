package im.hch.datareceiver.repositories;

import im.hch.datareceiver.dao.MarketDAO;
import im.hch.datareceiver.dao.SymbolDAO;
import im.hch.datareceiver.dao.SymbolPriceDAO;
import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;

import java.util.ArrayList;
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

    public SymbolPrice getLastPrice(Symbol symbol, SymbolPrice.PriceType type) {
        return symbolPriceDAO.findLastUpdate(symbol, type);
    }

    public void removePricesOfSymbol(Symbol symbol) {
        symbolPriceDAO.deletePricesOfSymbol(symbol);
    }
}
