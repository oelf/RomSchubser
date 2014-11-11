package de.romschubser;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpRequest extends AsyncTask<String, String, String> {

    public String postData(String url) {
        BufferedReader reader = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);

            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                str.append(line+":end:");
            }
            reader.close();

            return str.toString();
        } catch(Exception e) {
            Log.e("Error", e.toString());
        }

        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    Log.e("ERROR", "Exception while closing InputStream", e);
                }
            }
        }

        return "";
    }

    @Override
    protected String doInBackground(String... urls) {
        return this.postData(urls[0]);
    }
}