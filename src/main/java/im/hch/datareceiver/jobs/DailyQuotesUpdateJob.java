package im.hch.datareceiver.jobs;

import im.hch.datareceiver.datasource.DataSource;
import im.hch.datareceiver.datasource.DataSourceFactory;
import im.hch.datareceiver.model.Symbol;
import im.hch.datareceiver.model.SymbolPrice;
import im.hch.datareceiver.repositories.StockRepository;

import java.util.Calendar;
import java.util.List;

/**
 * This job retrieves the daily quotes for the symbols of the specified market.
 * The close time of different markets may be different.
 */
public class DailyQuotesUpdateJob extends BaseJob {
    private StockRepository repository;
    private DataSource dataSource;
    public static final int TEN_YEARS = 10 * 365;

    public DailyQuotesUpdateJob() {
        repository = new StockRepository();
        dataSource = DataSourceFactory.getDataSource();
    }

    @Override
    public String execute(Object[] args) {
        if (dataSource == null) {
            logger.error("Failed to create datasource.");
            return null;
        }

        String market = (String) args[0];

        List<Symbol> symbols = repository.getSymbolsOfMarket(market);
        for (Symbol symbol : symbols) {
            Calendar toDate = Calendar.getInstance(symbol.getMarket().getTimeZoneObj());
            Calendar fromDate = getFromDate(toDate, repository.getLastUpdateTime(symbol, SymbolPrice.PriceType.DAY));
            List<SymbolPrice> prices = dataSource.retrieveDailyPrices(symbol, fromDate.getTime(), toDate.getTime());
            repository.saveSymbolPrices(prices);
        }
        //TODO add execute log to db
        return null;
    }

    /**
     * Find out the from date of this update.
     * @param toDate current date
     * @param lastUpdateTime last update time
     * @return the from date
     */
    private Calendar getFromDate(Calendar toDate, Calendar lastUpdateTime) {
        Calendar fromDate =  Calendar.getInstance();
        if (lastUpdateTime == null) {
            fromDate.add(Calendar.DATE, -TEN_YEARS);
        } else {
            fromDate.setTime(lastUpdateTime.getTime());
            fromDate.add(Calendar.DATE, 1);
        }

        return fromDate;
    }
}
