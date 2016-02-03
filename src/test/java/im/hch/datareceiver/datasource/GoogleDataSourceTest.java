package im.hch.datareceiver.datasource;

import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

public class GoogleDataSourceTest {
    private static DataSource dataSource;

    @BeforeClass
    public static void setUpEntityManagerFactory() {
        dataSource = new GoogleDataSource(null);
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
    }

    @Test
    public void testRetrieveDailyPrices() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Symbol symbol = new Symbol("AAPL", "Apple Inc.",
                    new Market("NASDAQ", 570, 960, "America/New_York"),
                    null, null, null);
            List<SymbolPrice> prices = dataSource.retrieveDailyPrices(symbol,
                    format.parse("2016-02-01"), format.parse("2016-02-02"));
            Assert.assertEquals("Should return two day's prices", 2, prices.size());
        } catch (Exception ex) {
            Assert.assertNull("Should not get any exception.", ex);
        }
    }

    @Test
    public void testSpecialSymbol() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Symbol symbol = new Symbol("EPM^A", "Evolution Petroleum Corporation, Inc.",
                    new Market("AMEX", 570, 960, "America/New_York"),
                    null, null, null);
            List<SymbolPrice> prices = dataSource.retrieveDailyPrices(symbol,
                    format.parse("2016-02-01"), format.parse("2016-02-02"));
            Assert.assertEquals("Should return two day's prices", 2, prices.size());
        } catch (Exception ex) {
            Assert.assertNull("Should not get any exception.", ex);
        }
    }
}
