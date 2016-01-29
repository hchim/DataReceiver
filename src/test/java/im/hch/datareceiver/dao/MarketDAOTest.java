package im.hch.datareceiver.dao;

import im.hch.datareceiver.model.Market;
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
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
    }

    @Test
    public void testInsert() {
        // UTC time 15:30PM - 22:00PM
        Market nyse = new Market("NYSE", 570, 960, "America/New_York");
        dao.insert(nyse);

        Market market = (Market) dao.get(nyse.getId());
        Assert.assertNotNull("market should not be null", market);

        market.setName("TEST");
        dao.update(market);

        List<Object> objs = dao.getAll();
        Assert.assertTrue("should have one record", objs.size() > 1);

        long count = dao.count();
        Assert.assertTrue("should have one record", count > 1);

        dao.delete(market.getId());
    }

}
