package im.hch.datareceiver;

import im.hch.datareceiver.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Config {
    public static final String CONFIG_FILE = "config.json";
    // data sources
    public static final String DATASOURCE_GOOGLE = "google";
    public static final String DATASOURCE_QUANDL = "quandl";

    private JSONObject object;
    private ArrayList<JobConfig> jobConfigs;
    private DatabaseConfig dbConfig;
    private DataSourceConfig dataSource;
    private static Config instance;

    private Config() {
        object = JsonParser.jsonFromFile(CONFIG_FILE);
        JSONArray jobs = object.getJSONArray("jobs");
        jobConfigs = new ArrayList<JobConfig>();
        for (int i = 0; i < jobs.length(); i++) {
            jobConfigs.add(new JobConfig(jobs.getJSONObject(i)));
        }

        dbConfig = new DatabaseConfig(object.getJSONObject("db_config"));
        dataSource = new DataSourceConfig(object.getJSONObject("dataSource"));
    }

    public synchronized static Config getConfig() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    public boolean isValid() {
        return object != null;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSource;
    }

    public ArrayList<JobConfig> getJobConfigs() {
        return jobConfigs;
    }

    public static class JobConfig {
        public String name;
        public String cronExpression;
        public String className;
        public Object[] args;

        JobConfig(JSONObject job) {
            name = job.getString("name");
            className = job.getString("class");
            cronExpression = job.getString("cron_expression");

            JSONArray array = job.getJSONArray("args");
            if (array != null) {
                args = new Object[array.length()];
                for (int i = 0; i < args.length; i++) {
                    args[i] = array.get(i);
                }
            }
        }
    }

    public DatabaseConfig getDatabaseConfig() {
        return dbConfig;
    }

    public static class DatabaseConfig {
        public String host;
        public int port;
        public String dbName;
        public String username;
        public String password;

        DatabaseConfig(JSONObject obj) {
            host = obj.optString("host", "127.0.0.1");
            port = obj.optInt("port", 27017);
            dbName = obj.getString("db_name");
            username = obj.optString("username", "");
            password = obj.optString("password", "");
        }
    }

    public static class DataSourceConfig {
        public String name;
        public JSONArray params;

        DataSourceConfig(JSONObject obj) {
            name = obj.getString("name");
            params = obj.getJSONArray("params");

        }
    }
}
