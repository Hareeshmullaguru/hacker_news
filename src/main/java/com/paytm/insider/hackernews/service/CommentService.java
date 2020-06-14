package com.paytm.insider.hackernews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.jobs.CommentJob;
import com.paytm.insider.hackernews.repo.jpa.CommentRespository;
import com.paytm.insider.hackernews.repo.jpa.StoryRespository;

@Service
public class CommentService {

	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	@Autowired
	CommentRespository commentRespository;
	
	@Autowired
	CommentJob commentJob;
	
	public List<Comment> topTenCommentsofStory(Long storyId){
		
		List<Comment> topComments=commentRespository.findByStoryId(storyId);
		
		// if no comments found
		if(topComments.size() == 0) {
			// fetch the parent comments of the story
			StoryPojo storyPojo=hackerApiManager.getStoryInfo(storyId);
			commentJob.setCommentIds(storyPojo.getKids());
			commentJob.setParentId(storyPojo.getId());
			commentJob.run(); // run the job manually
			topComments=commentRespository.findByStoryId(storyId);
		}
		
		return topComments;
	}
}
