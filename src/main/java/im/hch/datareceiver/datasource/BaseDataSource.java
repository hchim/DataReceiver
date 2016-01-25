package im.hch.datareceiver.datasource;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public abstract class BaseDataSource implements DataSource{
    private Logger logger = Logger.getLogger(BaseDataSource.class);

    public List<SymbolPrice> retrieveDailyPrices(Symbol symbol, Date from, Date to) {
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
}
