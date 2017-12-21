package com.mrolorin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

// TODO: Implement other methods
public class HttpUrlConnectionClient implements HttpClient {

    public String post(String url, String urlParameters, Map<String, String> properties) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        properties.forEach(con::setRequestProperty);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();

        in.lines().forEach(response::append);
        in.close();

        return response.toString();
    }

}
