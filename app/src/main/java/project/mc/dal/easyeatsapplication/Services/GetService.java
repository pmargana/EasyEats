package project.mc.dal.easyeatsapplication.Services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by harika93 on 2017-11-11.
 */

public class GetService {
    private static final String TAG = "Http Connection";


    public String getResponseGET(String urlGET) {
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;
        String response=null;

        Integer result = 0;
        try {
                /* forming th java.net.URL object */
            URL url = new URL(urlGET);

            urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
            urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
            urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
            urlConnection.setRequestMethod("GET");

            int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode ==  200) {

                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                response = convertInputStreamToString(inputStream);


                result = 1; // Successful

            }else{
                result = 0; //"Failed to fetch data!";
                response=null;
            }

        } catch (Exception e) {
            // Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();

        }

        return response; //"Failed to fetch data!";
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }
}
