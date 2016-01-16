package im.hch.datareceiver.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonParser {

    public static JSONObject jsonFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            StringBuffer buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject object = new JSONObject(buffer.toString());
            return object;
        } catch (Exception e) {
            return null;
        }
    }
}
