package im.hch.datareceiver;

import im.hch.datareceiver.dao.MarketDAO;
import im.hch.datareceiver.dao.SymbolDAO;
import im.hch.datareceiver.dao.SymbolPriceDAO;
import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;

import java.util.ArrayList;

public class InitDB {

    public static void main(String[] args) {
        MarketDAO marketDAO = new MarketDAO();
        SymbolDAO symbolDAO = new SymbolDAO();
        SymbolPriceDAO symbolPriceDAO = new SymbolPriceDAO();

        //clear
        symbolPriceDAO.removeAll();
        symbolDAO.removeAll();
        marketDAO.removeAll();
        //insert date
        ArrayList<Market> markets = new ArrayList<Market>();
        markets.add(new Market("NYSE", 570, 960, "America/New_York"));
        markets.add(new Market("NASDAQ", 570, 960, "America/New_York"));
        markets.add(new Market("AMEX", 570, 960, "America/New_York"));

        marketDAO.insert(markets);
        //TODO add more symbols
        symbolDAO.insert(new Symbol("AAPL", "Apple Inc.", markets.get(1), "IT"));

        marketDAO.creatDefaultIndex();
    }
}
