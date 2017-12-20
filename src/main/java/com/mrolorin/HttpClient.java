package com.mrolorin;

import java.util.Map;

public interface HttpClient {
    String post(String url, String urlParameters, Map<String, String> properties) throws Exception;
}
