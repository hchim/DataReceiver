package im.hch.datareceiver;

import im.hch.datareceiver.scheduler.DataReceiverScheduler;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Main {
    private DataReceiverScheduler scheduler;
    private static Logger logger = Logger.getLogger(Main.class);
    private Config config;

    public static final String CONFIG_FILE = "config.json";

    public Main() {
        scheduler = DataReceiverScheduler.getInstance();
    }

    public void init() {
        config = new Config();

        if (!config.isValid()) {
            logger.fatal("Failed to parse config file: " + CONFIG_FILE);
            System.exit(-1);
        }

        initScheduler();
    }

    private void initScheduler() {
        ArrayList<Config.JobConfig> jobConfigs = config.getJobConfigs();
        for (int i = 0; i < jobConfigs.size(); i++) {
            Config.JobConfig jobConfig = jobConfigs.get(i);
            scheduler.addJob(jobConfig);
        }
    }

    public void startScheduler() {
        scheduler.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.startScheduler();
    }
}
