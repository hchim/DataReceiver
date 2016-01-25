package im.hch.datareceiver.datasource;

import im.hch.datareceiver.Config;

public class DataSourceFactory {
    private static Config config = Config.getConfig();

    public static final DataSource getDataSource() {
        if (config.getDataSource().equals(Config.DATASOURCE_GOOGLE)) {
            return new GoogleDataSource();
        }

        return null;
    }
}
