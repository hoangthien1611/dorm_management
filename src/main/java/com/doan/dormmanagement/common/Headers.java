package com.doan.dormmanagement.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Headers {
    public static HttpHeaders headers;

    private Headers() {}

    public static HttpHeaders getHeaders() {
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        }
        return headers;
    }
}
