package project.mc.dal.easyeatsapplication.Services;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by harika93 on 2017-11-11.
 */

public class HttpUrlConnectionPostService {


    public String  performPostCall(String requestURL, String jsonData) {
        URL url;
        HttpURLConnection urlConn;
        DataOutputStream printout=null;
        DataInputStream input;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestURL);
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            //urlConn.setRequestProperty("Host", "android.schoolportal.gr");
            urlConn.connect();

            String str = jsonData.toString();
            byte[] data=str.getBytes("UTF-8");
            printout = new DataOutputStream(urlConn.getOutputStream ());
            printout.write(data);
            printout.flush ();
            printout.close ();

            int HttpResult =urlConn.getResponseCode();
           /* if(HttpResult ==HttpURLConnection.HTTP_OK) {*/
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            //}



           /* // Send POST output.
            printout = new DataOutputStream(urlConn.getOutputStream ());
            printout.write(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
            printout.flush ();
            printout.close ();*/

        }catch (Exception e){
            e.printStackTrace();
        }
        return  sb.toString();
    }

}
