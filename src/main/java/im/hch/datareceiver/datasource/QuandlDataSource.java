package im.hch.datareceiver.datasource;

import im.hch.datareceiver.model.SymbolPrice;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuandlDataSource extends BaseDataSource {
    /*
        Example URI:
        https://www.quandl.com/api/v3/datasets/WIKI/FB/data.csv?start_date=2012-11-01&end_date=2013-11-30&api_key=YOURAPIKEYHERE
     */
    private final static String BASE_URL_TEMPLATE =
            "https://www.quandl.com/api/v3/datasets/%s/%s/data.csv?start_date=%s&end_date=%s&api_key=%s";
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private Logger logger = Logger.getLogger(GoogleDataSource.class);
    private static SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    /**
     * @param params [0]: api key; [1]: database
     */
    public QuandlDataSource(JSONArray params) {
        super(params);
    }

    @Override
    protected String getDailyUri(String market, String symbol, Date from, Date to) {
        symbol = symbol.replace('^', '_');
        return String.format(BASE_URL_TEMPLATE,
                params.optString(1), symbol, format.format(from), format.format(to), params.optString(0));
    }

    @Override
    protected SymbolPrice parseLine(String line) {
        try {
            // Date,Open,High,Low,Close,Volume,Dividend,Split,Adj_Open,Adj_High,Adj_Low,Adj_Close,Adj_Volume
            String[] strs = line.split(",");
            if (strs.length < 13) {
                return null;
            }

            Date time = format.parse(strs[0]);
            float open = Float.valueOf(strs[8]);
            float high = Float.valueOf(strs[9]);
            float low = Float.valueOf(strs[10]);
            float close = Float.valueOf(strs[11]);
            long volume = Double.valueOf(strs[12]).longValue();

            SymbolPrice symbolPrice = new SymbolPrice(SymbolPrice.PriceType.DAY, null, open,
                    high, low, close, volume, time);
            return symbolPrice;
        } catch (Exception ex) {
            logger.warn("Failed to parse response.");
            return null;
        }
    }
}
