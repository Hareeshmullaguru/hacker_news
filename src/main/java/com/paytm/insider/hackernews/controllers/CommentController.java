package com.paytm.insider.hackernews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.insider.hackernews.response.ServiceResponse;
import com.paytm.insider.hackernews.service.CommentService;

@RestController 
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@GetMapping(value = "/story/{id}/comments")
	public ServiceResponse<?> getTopTenStories(@PathVariable("id") Long storyId){
		return new ServiceResponse<>(commentService.topTenCommentsofStory(storyId));
	}
	
}
