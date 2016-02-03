package im.hch.datareceiver.datasource;

import im.hch.datareceiver.Config;

public class DataSourceFactory {
    private static Config config = Config.getConfig();

    public static final DataSource getDataSource() {
        Config.DataSourceConfig dsConfig = config.getDataSourceConfig();

        if (dsConfig.name.equals(Config.DATASOURCE_GOOGLE)) {
            return new GoogleDataSource(dsConfig.params);
        } else if (dsConfig.name.equals(Config.DATASOURCE_QUANDL)) {
            return new QuandlDataSource(dsConfig.params);
        }

        return null;
    }
}
