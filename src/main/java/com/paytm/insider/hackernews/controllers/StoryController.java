package com.paytm.insider.hackernews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.insider.hackernews.response.ServiceResponse;
import com.paytm.insider.hackernews.service.StoryService;

@RestController 
public class StoryController {
    
	@Autowired
	StoryService storyService;
	
	@GetMapping(value = "/top-stories")
	public ServiceResponse<?> getTopTenStories(){
		return new ServiceResponse<>(storyService.findTopTenStories());
	}
	
	@GetMapping(value = "/past-stories")
	public ServiceResponse<?> getPastStories(){
		return new ServiceResponse<>(storyService.findPastStories());
	}
	
}
