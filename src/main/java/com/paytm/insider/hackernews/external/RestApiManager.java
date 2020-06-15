package com.paytm.insider.hackernews.external;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class RestApiManager {

  @Autowired
  private RestTemplate restTemplate;


  public <T> T get(String baseUrl, String url, String query, HttpHeaders requestHeaders,
                   Class<T> responseClassType, String logId) {
    long startTime = System.currentTimeMillis();
    ResponseEntity<T> responseEntity = null;
    try {
      String fullUrl = getFullUrl(baseUrl, url, query);
      HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestHeaders);
      
      System.out.println(" full url " + fullUrl);
      responseEntity =
          restTemplate.exchange(fullUrl, HttpMethod.GET, requestEntity, responseClassType);

      if (responseEntity.getStatusCode() == HttpStatus.OK) {
          return responseEntity.getBody();
      }
    } catch (Exception e) {
       System.out.println(" exception " + e.getMessage());
    }
    return null;
  }

  private String getFullUrl(String baseUrl, String url, String query) {
    StringBuilder fullUrl = new StringBuilder();
    fullUrl.append(baseUrl);
    if (url != null) {
      fullUrl.append(url);
    }
    if (query != null && query.startsWith("?")) {
      query = query.substring(1);
    }
    query = StringUtils.trimToNull(query);
    if (query != null) {
      fullUrl.append("?");
      fullUrl.append(query);
    }
    return fullUrl.toString();
  }

}

