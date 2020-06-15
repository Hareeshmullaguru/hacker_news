package com.paytm.insider.hackernews.dto;

import lombok.Data;

@Data
public class CommentDto {
	
	private String text;
	private String user_name;
	private Integer age;
}
