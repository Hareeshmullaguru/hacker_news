package com.paytm.insider.hackernews.dto;

import java.time.LocalDateTime;

import com.paytm.insider.hackernews.util.LocalDateTimeConverter;

import lombok.Data;

@Data
public class StoryDto {
      
	private String title;
	private String url;
	private Integer score;
	private LocalDateTime time_of_submission;
	private String submitted_by;
	
}

