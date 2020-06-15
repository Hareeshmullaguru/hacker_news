package com.paytm.insider.hackernews.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="story_id")
	private Long storyId;
	
	@Column(name="comment_id", unique=true)
	private Long commentId;
	
	@Column(name="added_by")
	private String addedBy;
	
	@Column(name="text",columnDefinition="TEXT")
	private String text;
	
	
}
