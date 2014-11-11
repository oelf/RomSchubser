package de.romschubser.kernels;

import android.os.Build;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.romschubser.Config;
import de.romschubser.HttpRequest;

public class Franco implements Kernel{

    private String kernel = "Franco.Kernel";

    private String device = "";
    private String url = "";

    private String version = "";  // device kernel version
    private String availableVersion = ""; // available kernel version

    private Vector<String[]> arrKernels = new Vector();

    public Franco(String kernelString) {
        this.initDevice(kernelString);
    }

    private void initDevice(String kernelString) {
        if(Build.PRODUCT.equals("bacon")) {
            this.device = "OnePlusOne";
            this.url = Config.URL_KERNEL_FRANCO.replace(":device:", device);
        }

        Pattern pattern = Pattern.compile("\\#(\\d{1,3})");
        Matcher matcher = pattern.matcher(kernelString);
        if(matcher.find()) {
            this.version = matcher.group(1);
        }

        if(this.device.length() > 0) {
            this.loadCurrentKernels();
        }
    }

    public boolean isNewest() {
        return (Integer.parseInt(this.version) >= Integer.parseInt(this.availableVersion));
    }

    public String getKernel() {
        return this.kernel;
    }

    public String getAvailableVersion() {
        return this.availableVersion;
    }

    public String getVersion() {
        return "#"+this.version;
    }

    private void loadCurrentKernels() {
        try {
            String data = new HttpRequest().execute(this.url).get();
            String[] arrData = data.split(":end:");
            for(int i = 0; i < arrData.length; i++) {
                String line = arrData[i];

                Pattern pattern = Pattern.compile("<a href=\"(.*\\.zip)\".");
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    String url = matcher.group(1);
                    String text = "";

                    pattern = Pattern.compile("r(\\d{1,3}).");
                    matcher = pattern.matcher(url);
                    if(matcher.find()) {
                        text =  matcher.group(1);
                    }

                    this.arrKernels.addElement(new String[]{url, text});
                }
            }

            if(!this.arrKernels.isEmpty()) {
                this.availableVersion = this.arrKernels.lastElement()[1];
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}