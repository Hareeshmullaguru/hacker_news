package com.paytm.insider.hackernews.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paytm.insider.hackernews.dto.CommentDto;
import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.entity.User;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
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
	UserService userService;
	
	@Autowired
	StoryService storyService;
	
	@Autowired
	CommentJob commentJob;
	
	public List<CommentDto> topTenCommentsofStory(Long storyId){
		
		List<Comment> topComments=commentRespository.findByStoryId(storyId);
		
		// if no comments found
		if(topComments.size() == 0) {
			// fetch the parent comments of the story
			StoryPojo storyPojo=storyService.getStoryInfo(storyId);
			if(storyPojo == null) {
				return null;
			}
			commentJob.setCommentIds(storyPojo.getKids());
			commentJob.setParentId(storyPojo.getId());
			commentJob.run(); // run the job manually
			topComments=commentRespository.findByStoryId(storyId);
		}
		
		List<CommentDto> commentResponse=new ArrayList<CommentDto>();
		
		// traverse the comments arranged in format
		for(Comment comment: topComments) {
			CommentDto commentDto=new CommentDto();
			User user=userService.getUser(comment.getAddedBy());
			commentDto.setAge(user.getAgeofProfile());
			commentDto.setText(comment.getText());
			commentDto.setUser_name(user.getName());
			commentResponse.add(commentDto);
		}
		return commentResponse;
	}
		
	public CommentPojo getCommentInfo(Long commentId) {
		//System.out.println(" comment Id " + commentId);
		return hackerApiManager.getCommentInfo(commentId);
	}
	
	public void deleteCommentByStoryId(Long storyId) {
		 commentRespository.deleteByStoryId(storyId);
	}
	
	public void saveComment(Comment comment) {
		//System.out.println(" save comment ");
		commentRespository.save(comment);
	}
	
	public void deleteAllComments() {
		commentRespository.deleteAll();
	}
	
}
