package com.paytm.insider.hackernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories(value = {"com.paytm.insider.hackernews.repo.jpa"})
public class HackerServiceApplication 
{
	public static void main(String[] args) {
	    SpringApplication.run(HackerServiceApplication.class, args);
	  }
}
