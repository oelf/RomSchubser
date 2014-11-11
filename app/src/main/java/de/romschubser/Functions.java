package de.romschubser;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Functions {

    /**
     * Returns a SystemProperty
     *
     * @param command The command
     * @return The Property, or NULL if not found
     */
    public static String getSystemProperty(String command) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        }
        catch (IOException ex) {
            Log.e("ERROR", "Unable to read sysprop " + command, ex);
            return null;
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    Log.e("ERROR", "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }
}
