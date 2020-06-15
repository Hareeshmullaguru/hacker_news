package com.paytm.insider.hackernews.external.pojo.hackernews;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class StoryPojo {

	private Long id;
	private String by;
	private Integer descendants;
	private List<Long> kids;
	private Integer score;
	private Long time;
	private String title;
	private String type;
	private String url;
	
}
