package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class SymbolPriceDAOTest {
    private static MarketDAO marketDAO;
    private static SymbolPriceDAO symbolPriceDAO;
    private static SymbolDAO symbolDAO;

    @BeforeClass
    public static void setUpEntityManagerFactory() {
        marketDAO = new MarketDAO();
        symbolPriceDAO = new SymbolPriceDAO();
        symbolDAO = new SymbolDAO();
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
    }

    @Test
    public void test() {
        // UTC time 15:30PM - 22:00PM
        Market nyse = new Market("NYSE", 570, 960, "America/New_York");
        marketDAO.insert(nyse);

        Symbol aapl = new Symbol("AAPL", "Apple Inc.", nyse, null, null, null);
        symbolDAO.insert(aapl);

        long count = symbolPriceDAO.count();
        symbolPriceDAO.insert(new SymbolPrice(SymbolPrice.PriceType.DAY, aapl, 0, 0, 0, 0, 0, new Date()));
        Assert.assertEquals("Should added one object.", count + 1, symbolPriceDAO.count());
        symbolPriceDAO.deletePricesOfSymbol(aapl);
        Assert.assertEquals("Should have the same number of prices.", count, symbolPriceDAO.count());

        symbolDAO.delete(aapl.getId());
        marketDAO.delete(nyse.getId());
    }

}
