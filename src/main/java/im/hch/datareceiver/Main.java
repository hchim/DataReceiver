package im.hch.datareceiver;

import im.hch.datareceiver.scheduler.DataReceiverScheduler;

public class Main {

    public static void main(String[] args) {
        DataReceiverScheduler scheduler = DataReceiverScheduler.getInstance();
        Object[] params = new Object[] {"http://www.google.com"};
        String exp = "0/10 * * * * ?";
        scheduler.addJob("test", "im.hch.datareceiver.jobs.CurlJob", exp, params);
        scheduler.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
