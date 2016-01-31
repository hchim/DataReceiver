package im.hch.datareceiver;

import im.hch.datareceiver.dao.*;
import im.hch.datareceiver.model.CronJob;
import im.hch.datareceiver.model.Market;
import im.hch.datareceiver.model.Symbol;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class InitDB {
    private MarketDAO marketDAO = new MarketDAO();
    private SymbolDAO symbolDAO = new SymbolDAO();
    private SymbolPriceDAO symbolPriceDAO = new SymbolPriceDAO();
    private CronJobDAO cronJobDAO = new CronJobDAO();
    private CronLogDAO cronLogDAO = new CronLogDAO();

    public void initDatabase() {
        clearAllCollections();
        initMarketAndSymbols();
        initJobs();
        //create index
        marketDAO.creatDefaultIndex();
    }

    private void clearAllCollections() {
        symbolPriceDAO.removeAll();
        symbolDAO.removeAll();
        marketDAO.removeAll();
        cronJobDAO.removeAll();
        cronLogDAO.removeAll();
    }

    private void initMarketAndSymbols() {
        ArrayList<Market> markets = new ArrayList<Market>();
        markets.add(new Market("NYSE", 570, 960, "America/New_York"));
        markets.add(new Market("NASDAQ", 570, 960, "America/New_York"));
        markets.add(new Market("AMEX", 570, 960, "America/New_York"));
        marketDAO.insert(markets);

        for (Market market : markets) {
            addSymbolsOfMarket(market);
        }
    }

    private void addSymbolsOfMarket(Market market) {
        try {
            Reader in = new FileReader(String.format("data/%s_symbols.csv", market.getName()));
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            Iterator<CSVRecord> it = records.iterator();
            it.next(); //skip header

            // "Symbol","Name","LastSale","MarketCap","IPOyear","Sector","industry","Summary Quote"
            while (it.hasNext()) {
                CSVRecord record = it.next();

                String symbol = record.get(0);
                String name = record.get(1);
                String ipoYear = record.get(4);
                if (ipoYear.equals("n/a")) {
                    ipoYear = null;
                }
                String sector = record.get(5);
                if (sector.equals("n/a")) {
                    sector = null;
                }
                String industry = record.get(6);
                if (industry.equals("n/a")) {
                    industry = null;
                }
                symbolDAO.insert(new Symbol(symbol, name, market, ipoYear, sector, industry));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initJobs() {
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
    }

    public static void main(String[] args) {
        InitDB initDB = new InitDB();
        initDB.initDatabase();
    }
}
