package im.hch.datareceiver.jobs;

import java.net.MalformedURLException;
import java.net.URL;

public class CurlJob extends CommandJob {

    @Override
    public String getCommand(Object[] args) {
        if (!validArgs(args)) {
            return null;
        }

        String command = "curl " + (String)args[0];
        return command;
    }

    @Override
    public boolean validArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return false;
        }

        try {
            URL url = new URL((String) args[0]);
            return true;
        } catch (MalformedURLException ex) {
            appendExecLog("Wrong URL: " + args[0]);
            return false;
        }
    }
}
