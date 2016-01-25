package im.hch.datareceiver.datasource;

import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class GoogleDataSource extends BaseDataSource {
    /*
        q = Company Symbol
        startdate = Start date of the historical prices
        enddate = End date of the historical prices
        histperiod = weekly or daily history periods
        start = index on which to display the historical price
        output = output the data in a format
    */
    private final static String BASE_URL_TEMPLATE =
            "http://www.google.com/finance/historical?q=%s:%s&startdate=%s&enddate=%s&output=csv";
    private final static String DATE_FORMAT = "dd-MMM-yy";

    private Logger logger = Logger.getLogger(GoogleDataSource.class);
    private static SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    public String getDailyUri(String market, String symbol, Date from, Date to) {
        return String.format(BASE_URL_TEMPLATE, market, symbol,
                format.format(from), format.format(to));
    }

    public List<SymbolPrice> parseResponseBody(String body, Symbol symbol) {
        List<SymbolPrice> prices = new ArrayList<SymbolPrice>();
        BufferedReader reader = new BufferedReader(new StringReader(body));
        try {
            reader.readLine(); //skip first line
            String line;
            String[] strs;
            while ((line = reader.readLine()) != null) {
                //Date,Open,High,Low,Close,Volume
                //30-Jan-09,13.23,13.37,12.86,12.88,162955387
                strs = line.split(",");
                if (strs.length < 6) {
                    continue;
                }

                Date time = format.parse(strs[0]);
                float open = Float.valueOf(strs[1]);
                float high = Float.valueOf(strs[2]);
                float low = Float.valueOf(strs[3]);
                float close = Float.valueOf(strs[4]);
                long volume = Long.valueOf(strs[5]);

                prices.add(new SymbolPrice(SymbolPrice.PriceType.DAY, symbol, open,
                        high, low, close, volume, time));
            }
            logger.info(String.format("Parsed the daily price of symbol: " + symbol.getSymbol()));
            return prices;
        } catch (Exception ex) {
            logger.warn("Failed to parse response.");
            return prices;
        }
    }
}
