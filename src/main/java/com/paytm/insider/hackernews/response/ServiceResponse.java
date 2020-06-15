package com.paytm.insider.hackernews.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResponse<T> extends ResponseEntity<T> {
	
	public ServiceResponse(T data) {
	    super(data, HttpStatus.OK);
	  }

	  public ServiceResponse(T data, HttpStatus status) {
	    super(data, status);
	  }
}
