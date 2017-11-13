package project.mc.dal.easyeatsapplication.Services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by harika93 on 2017-11-11.
 */

public class HttpUrlConnectionPUTservice {
    public String  performPostCall(String requestURL, String jsonData) {
          DataOutputStream printout=null;
        StringBuilder sb = new StringBuilder();

        try{

            URL url = new URL(requestURL);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(jsonData.toString());
            out.close();

            int HttpResult =httpCon.getResponseCode();
            System.out.println("Put responseCode = " + HttpResult);

           /* if(HttpResult ==HttpURLConnection.HTTP_OK) {*/
            BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
        }catch(Exception e)
        {
            System.out.println("Put Expresponse =");
            e.printStackTrace();

        }


        return  sb.toString();

    }
}
