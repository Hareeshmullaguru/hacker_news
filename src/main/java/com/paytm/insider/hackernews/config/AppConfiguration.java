package com.paytm.insider.hackernews.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@EnableAsync
@Configuration
@Component
public class AppConfiguration  {

  @Value("${httpClient.connection.pool.size:10}")
  private Integer poolMaxTotal;

  @Value("${httpClientFactory.connection.timeout:5000}")
  private Integer connectionTimeOut;

  @Value("${httpClientFactory.read.timeout:5000}")
  private Integer readTimeOut;


  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
    restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    return restTemplate;
  }

  private ClientHttpRequestFactory httpRequestFactory() {
	    HttpComponentsClientHttpRequestFactory factory =
	            new HttpComponentsClientHttpRequestFactory(httpClient());
	    factory.setConnectTimeout(connectionTimeOut);
	    factory.setReadTimeout(readTimeOut);
	    return factory; 
 }

	    private HttpClient httpClient() {
	        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
	        connectionManager.setMaxTotal(poolMaxTotal);
	        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	    }

 

}

