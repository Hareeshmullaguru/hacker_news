package com.paytm.insider.hackernews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paytm.insider.hackernews.entity.Story;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.repo.jpa.StoryRespository;

@Service
public class StoryService {
    
	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	@Autowired
	StoryRespository storyRespository;
	
	@Autowired
	AsycService asyncService;
	
	public List<Story> findTopTenStories(){
		
		// check in the table if exists return it		
		List<Story> topStoires=storyRespository.getOldOrNewStories(true);
		
		//if stories found
		if(topStoires.size() != 0) {
			// else run the async job and fetch it from the database
			asyncService.findTopStories();
			topStoires=storyRespository.getOldOrNewStories(true);
		}
	
		return topStoires;
	}
	
	public List<Story> findPastStories(){
		return storyRespository.getOldOrNewStories(false);
	}
}
