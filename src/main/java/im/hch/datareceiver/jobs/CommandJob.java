package im.hch.datareceiver.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class CommandJob extends BaseJob {

    public abstract String getCommand(Object[] args);
    public abstract boolean validArgs(Object[] args);

    @Override
    public String execute(Object[] args) {
        String command = getCommand(args);
        if (command == null) {
            return null;
        }

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuffer buff = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buff.append(line).append("\n");
            }

            return buff.toString();
        } catch (IOException ioe) {
            logger.warn("Failed to execute command: " + command);
            return null;
        }
    }
}
