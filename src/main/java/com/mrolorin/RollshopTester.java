package com.mrolorin;

import com.mrolorin.bruteforcer.Tester;

import java.util.HashMap;
import java.util.Map;

public class RollshopTester implements Tester {

    private HttpClient httpClient;
    private static final String URL = "http://www.rollshop.co.il/test.php";

    public RollshopTester(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean test(String password) {
        String result;
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 " + password + System.currentTimeMillis());
        headers.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        try {
            String response = this.httpClient.post(URL, "code=" + password, headers);
            result = this.parseHttpResponse(response);
        } catch (Exception e) {
            System.out.println("\"" + e.getMessage() + "\" on \"" + password + "\". Retrying...");
            // TODO: Limit the number of attempts
            return this.test(password);
        }
        boolean isSuccess = isSuccess(result);
        if (isSuccess) {
            System.out.println("Success with body \"" + result + "\"");
        }
        return this.isSuccess(result);
    }

    private boolean isSuccess(String body) {
        return !"WRONG =(".equals(body);
    }

    private String parseHttpResponse(String response) {
        String beginSequence = "<body>";
        String endSequence = "</body>";
        int beginIndex = response.indexOf(beginSequence) + beginSequence.length();
        int endIndex = response.indexOf(endSequence);
        return response.substring(beginIndex, endIndex).trim();
    }

}
