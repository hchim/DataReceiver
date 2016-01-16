package im.hch.datareceiver.jobs;

import org.quartz.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            return false;
        }
    }
}
