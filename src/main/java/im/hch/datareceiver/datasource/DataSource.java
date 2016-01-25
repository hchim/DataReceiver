package im.hch.datareceiver.datasource;

import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;

import java.util.Date;
import java.util.List;

public interface DataSource {

    public List<SymbolPrice> retrieveDailyPrices(Symbol symbol, Date from, Date to);

    public String getDailyUri(String market, String symbol, Date from, Date to);

    public List<SymbolPrice> parseResponseBody(String body, Symbol symbol);
}
