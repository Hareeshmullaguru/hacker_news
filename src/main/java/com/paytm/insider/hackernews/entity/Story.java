package com.paytm.insider.hackernews.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.paytm.insider.hackernews.util.LocalDateTimeConverter;

import lombok.Data;

@Entity
@Table(name = "stories")
@Data
public class Story {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="story_id")
	private Long storyId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="url")
	private String url;
	
	@Column(name="score")
	private Integer score;
	
	@Column(name = "submitted_time")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdAt;
	
	@Column(name="submitted_by")
	private String submittedBy;
	
	@Column(name="is_top_story")
	private Boolean isTopStory;
}
