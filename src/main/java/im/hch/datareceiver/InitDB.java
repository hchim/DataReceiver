package im.hch.datareceiver;

import im.hch.datareceiver.dao.CronJobDAO;
import im.hch.datareceiver.dao.MarketDAO;
import im.hch.datareceiver.dao.SymbolDAO;
import im.hch.datareceiver.dao.SymbolPriceDAO;
import im.hch.datareceiver.model.CronJob;
import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;

import java.util.ArrayList;

public class InitDB {

    public static void main(String[] args) {
        MarketDAO marketDAO = new MarketDAO();
        SymbolDAO symbolDAO = new SymbolDAO();
        SymbolPriceDAO symbolPriceDAO = new SymbolPriceDAO();
        CronJobDAO cronJobDAO = new CronJobDAO();

        //clear
        symbolPriceDAO.removeAll();
        symbolDAO.removeAll();
        marketDAO.removeAll();
        cronJobDAO.removeAll();
        //insert date
        ArrayList<Market> markets = new ArrayList<Market>();
        markets.add(new Market("NYSE", 570, 960, "America/New_York"));
        markets.add(new Market("NASDAQ", 570, 960, "America/New_York"));
        markets.add(new Market("AMEX", 570, 960, "America/New_York"));

        marketDAO.insert(markets);
        //TODO add more symbols
        symbolDAO.insert(new Symbol("AAPL", "Apple Inc.", markets.get(1), "IT"));
//        cronJobDAO.insert(
//                new CronJob("daily-NASDAQ", "0/30 * * * * ?", "im.hch.datareceiver.jobs.DailyQuotesUpdateJob",
//                        new String[] {"NASDAQ"}, "America/New_York", true)
//        );
        cronJobDAO.insert(
                new CronJob("daily-NASDAQ", "0 30 16 ? * MON-FRI", "im.hch.datareceiver.jobs.DailyQuotesUpdateJob",
                        new String[] {"NASDAQ"}, "America/New_York", true)
        );
        cronJobDAO.insert(
                new CronJob("daily-NYSE", "0 30 16 ? * MON-FRI", "im.hch.datareceiver.jobs.DailyQuotesUpdateJob",
                        new String[] {"NYSE"}, "America/New_York", true)
        );
        cronJobDAO.insert(
                new CronJob("daily-AMEX", "0 30 16 ? * MON-FRI", "im.hch.datareceiver.jobs.DailyQuotesUpdateJob",
                        new String[] {"AMEX"}, "America/New_York", true)
        );

        marketDAO.creatDefaultIndex();
    }
}
