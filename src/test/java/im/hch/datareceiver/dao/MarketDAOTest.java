package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class MarketDAOTest {
    private static MarketDAO dao;

    @BeforeClass
    public static void setUpEntityManagerFactory() {
        dao = new MarketDAO();
        dao.removeAll();
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
    }

    @Test
    public void testInsert() {
        // UTC time 15:30PM - 22:00PM
        Market nyse = new Market("NYSE", 930, 1320);
        dao.insert(nyse);

        Market market = (Market) dao.get(nyse.getId());
        Assert.assertNotNull("market should not be null", market);

        market.setName("TEST");
        dao.update(market);

        List<Object> objs = dao.getAll();
        Assert.assertTrue("should return only one record", objs.size() == 1);

        long count = dao.count();
        Assert.assertTrue("should have only one record", count == 1);

        dao.delete(market.getId());
    }

}
