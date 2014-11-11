package de.romschubser.roms;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.romschubser.Config;
import de.romschubser.Functions;
import de.romschubser.HttpRequest;

public class Cyanogenmod implements Rom {

    private String rom = "CyanogenMod";

    private String device = "";
    private String url = "";

    private String version = "";
    private String availableVersion = "";

    private Vector<String[]> arrRoms = new Vector();

    public Cyanogenmod(String romString) {
        this.initDevice(romString);
    }

    private void initDevice(String romString) {
        Pattern pattern = Pattern.compile("(\\d{8})");
        Matcher matcher = pattern.matcher(romString);
        if(matcher.find()) {
            this.version = matcher.group(1);
        }

        this.url = Config.URL_ROM_CM.replace(":device:", Functions.getSystemProperty("getprop ro.cm.device"));

        if(this.version.length() > 0) {
            this.loadCurrentRoms();
        }
    }

    public String getRom() {
        return this.rom;
    }

    public String getVersion() {
        return this.version;
    }

    public String getAvailableVersion() {
        return this.availableVersion;
    }

    public boolean isNewest() {
        String DATE_FORMAT = "yyyyMMd";
        boolean isNewest = false;

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date dVersion = df.parse(this.version);
            Date dAvailableVersion = df.parse(this.availableVersion);

            isNewest = (dAvailableVersion.before(dVersion) || this.availableVersion.equals(this.version));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isNewest;
    }

    private void loadCurrentRoms() {
        try {
            String data = new HttpRequest().execute(this.url).get();
            String[] arrData = data.split(":end:");
            for(int i = 0; i < arrData.length; i++) {
                String line = arrData[i];

                if(line.toLowerCase().contains("nightly")) {
                    Pattern pattern = Pattern.compile("<a href=\"(.*\\.zip)\".");
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()) {
                        String url = matcher.group(1);
                        String text = url.replace(Build.PRODUCT+".zip", "");

                        this.arrRoms.addElement(new String[]{url, text});
                    }
                }
            }

            if(!this.arrRoms.isEmpty()) {
                Pattern pattern = Pattern.compile("(\\d{8})");
                Matcher matcher = pattern.matcher(this.arrRoms.firstElement()[1]);
                if(matcher.find()) {
                    this.availableVersion = matcher.group(1);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
