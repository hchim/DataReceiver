package im.hch.datareceiver.datasource;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseDataSource implements DataSource{
    private Logger logger = Logger.getLogger(BaseDataSource.class);
    protected JSONArray params;

    public BaseDataSource(JSONArray params) {
        this.params = params;
    }

    protected abstract String getDailyUri(String market, String symbol, Date from, Date to);
    protected abstract SymbolPrice parseLine(String line);

    public List<SymbolPrice> retrieveDailyPrices(Symbol symbol, Date from, Date to) {
        if (symbol == null || symbol.getMarket() == null
                || from == null || to == null || to.getTime() < from.getTime()) {
            return null;
        }

        String market = symbol.getMarket().getName();
        try {
            String uri = getDailyUri(market, symbol.getSymbol(), from, to);
            HttpResponse<String> response = Unirest.get(uri).asString();
            String body = response.getBody().trim();
            return parseResponseBody(body, symbol);
        } catch (UnirestException e) {
            logger.warn(String.format("Failed to load prices for symbol: %s:%s", market, symbol.getSymbol()));
            return null;
        }
    }

    protected List<SymbolPrice> parseResponseBody(String body, Symbol symbol) {
        List<SymbolPrice> prices = new ArrayList<SymbolPrice>();
        BufferedReader reader = new BufferedReader(new StringReader(body));
        try {
            reader.readLine(); //skip first line
            String line;
            while ((line = reader.readLine()) != null) {
                SymbolPrice symbolPrice = parseLine(line);
                if (symbolPrice != null) {
                    symbolPrice.setSymbol(symbol);
                    prices.add(symbolPrice);
                }
            }
            logger.info(String.format("Parsed the daily price of symbol: " + symbol.getSymbol()));
            return prices;
        } catch (Exception ex) {
            logger.warn("Failed to parse response.");
            return prices;
        }
    }
}
