package im.hch.datareceiver.jobs;

public class DailyQuotesUpdateJob extends BaseJob {

    @Override
    public String execute(Object[] args) {
        String dataSource = (String)args[0];
        return null;
    }

}
