package com.paytm.insider.hackernews.external.pojo.hackernews;

import java.util.List;

import lombok.Data;

@Data
public class UserPojo {

	private String id;
	private String about;
	private Long created;
	private Long delay;
	private Long karma;
	private List<Long> submitted;
	
}
