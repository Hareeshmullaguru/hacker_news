package com.paytm.insider.hackernews.external.pojo.hackernews;

import java.util.List;

import lombok.Data;

@Data
public class CommentPojo {

	private Long id;
	private String by;
	private List<Long> kids;
	private Long parent;
	private Long time;
	private String text;
	private String type;
}