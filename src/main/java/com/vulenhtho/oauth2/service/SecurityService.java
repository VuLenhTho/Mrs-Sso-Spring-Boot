package com.vulenhtho.oauth2.service;

import org.springframework.http.HttpHeaders;

public interface SecurityService {
    String getUserName();

    HttpHeaders getHeadersWithToken();

    HttpHeaders getHeaders();

    String getToken();

    String getShortToken();
}
