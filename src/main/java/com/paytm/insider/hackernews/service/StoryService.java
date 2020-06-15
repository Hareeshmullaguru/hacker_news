package com.paytm.insider.hackernews.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paytm.insider.hackernews.dto.StoryDto;
import com.paytm.insider.hackernews.entity.Story;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.repo.jpa.StoryRespository;

@Service
public class StoryService {
    
	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	@Autowired
	StoryRespository storyRespository;
	
	@Autowired
	AsycService asyncService;
	
	public List<StoryDto> findTopTenStories(){
		
		// check in the table if exists return it		
		List<Story> topStoires=storyRespository.getOldOrNewStories(true);
		
		//if stories found
		if(topStoires.size() == 0) {
			// else run the async job and fetch it from the database
			asyncService.findTopStories();
			topStoires=storyRespository.getOldOrNewStories(true);
		}
	
		// convert to actual response
		List<StoryDto>  storyDtos=new ArrayList<StoryDto>();
		for(Story story: topStoires) {
			StoryDto storyDto=new StoryDto();
			storyDto.setTitle(story.getTitle());
			storyDto.setScore(story.getScore());
			storyDto.setTime_of_submission(story.getCreatedAt());
			storyDto.setUrl(story.getUrl());
			storyDto.setSubmitted_by(story.getSubmittedBy());
			storyDtos.add(storyDto);
		}
		return storyDtos;
	}
	
	public List<StoryDto> findPastStories(){
		List<Story> pastStories= storyRespository.getOldOrNewStories(false);
		// convert to actual response
		List<StoryDto>  storyDtos=new ArrayList<StoryDto>();
		for(Story story: pastStories) {
			StoryDto storyDto=new StoryDto();
			storyDto.setTitle(story.getTitle());
			storyDto.setScore(story.getScore());
			storyDto.setTime_of_submission(story.getCreatedAt());
			storyDto.setUrl(story.getUrl());
			storyDto.setSubmitted_by(story.getSubmittedBy());
			storyDtos.add(storyDto);
		}
		return storyDtos;
	}
	
	public List<Integer> getTopStories(){
		return hackerApiManager.getTopStories();
	} 
	
	public StoryPojo getStoryInfo(Long storyId) {
		return hackerApiManager.getStoryInfo(storyId);
	}
	
	public void deleteOldStories() {
		storyRespository.deleteOldStories();
	}
	
	public void changeNewToOldStories() {
		storyRespository.changeNewToOldStories();
	}
	
	public void saveStory(Story story) {
		storyRespository.save(story);
	}
	

}
