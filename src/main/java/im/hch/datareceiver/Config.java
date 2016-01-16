package im.hch.datareceiver;

import im.hch.datareceiver.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Config {
    public static final String CONFIG_FILE = "config.json";
    private JSONObject object;
    private ArrayList<JobConfig> jobConfigs;

    public Config() {
        object = JsonParser.jsonFromFile(CONFIG_FILE);
        JSONArray jobs = object.getJSONArray("jobs");
        jobConfigs = new ArrayList<JobConfig>();
        for (int i = 0; i < jobs.length(); i++) {
            jobConfigs.add(new JobConfig(jobs.getJSONObject(i)));
        }
    }

    public boolean isValid() {
        return object != null;
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
}
