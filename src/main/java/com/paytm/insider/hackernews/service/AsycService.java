package com.paytm.insider.hackernews.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.entity.Story;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.jobs.CommentJob;
import com.paytm.insider.hackernews.repo.jpa.CommentRespository;
import com.paytm.insider.hackernews.repo.jpa.StoryRespository;
import com.paytm.insider.hackernews.util.PairParentChild;

@Service
public class AsycService {

	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	@Autowired
	StoryRespository  storyRespository;
	
	@Autowired
	CommentJob commentJob;
	
	@Autowired
	CommentRespository  commentRepository;
	
	
	@Scheduled(fixedRate = 600000)  // every 10 minutes run this job (1000 * 60) * 10
	@Transactional
	public void findTopStories(){
		
		// get the top 500 stories from the hacker news api
        List<Integer> topStoires=hackerApiManager.getTopStories();
		
       // System.out.println(" top stories " + topStoires);
        
        
	
        // check the size of the return list
        if(topStoires.size() > 0) {
        	
        	
        	Comparator<StoryPojo> scoreComparator=(storyScores1,storyScores2) -> storyScores2.getScore().compareTo(storyScores1.getScore());
        	PriorityQueue<StoryPojo> storyScores=new PriorityQueue<StoryPojo>(scoreComparator);
        	for(Integer storyId: topStoires) {
        		// find the score of each operator
        		StoryPojo storyPojo=hackerApiManager.getStoryInfo(storyId.longValue());
        		if(storyPojo != null) {
        			storyScores.add(storyPojo);
        		}
        		
    		}
        	
        	// if returned list not null
        	if(storyScores.size() > 0) {
        		
        		// remove the comments of the top stories
        		commentRepository.deleteAll();
        		
        		// remove the old stories
        		storyRespository.deleteOldStories();
        		
        		// change the new to old stories
        		storyRespository.changeNewToOldStories();
        		
        		// update the top stories into 
        		int i=2;
        		while(i > 0 && !storyScores.isEmpty()) {
        			StoryPojo storyPojo=storyScores.poll(); // get the top story
        			Story story=convertStoryPojotoStory(storyPojo); // convert into data object format
        			storyRespository.save(story); // store into data base
        			//Runnable commentJob=new CommentJob(storyPojo.getKids(),story.getStoryId()); // find the top 10 comments for this story store into DB
        			commentJob.setCommentIds(storyPojo.getKids());
        			commentJob.setParentId(story.getStoryId());
        			new Thread(commentJob).start(); // execute the parallel thread
        			i--;
        		}
        	}
        	
        }
        else {
        	
        	// nothing do
        	System.out.println(" No top stories found");
        }
		
		
	}
	
	
	private Story convertStoryPojotoStory(StoryPojo storyPojo) {
		
		Story story=new Story();
		story.setStoryId(storyPojo.getId());
		story.setTitle(storyPojo.getTitle());
		story.setCreatedAt(LocalDateTime.ofEpochSecond(storyPojo.getTime(), 0, ZonedDateTime.now().getOffset()));
		story.setIsTopStory(true);
		story.setSubmittedBy(storyPojo.getBy());
		story.setUrl(storyPojo.getUrl());
		story.setScore(storyPojo.getScore());
		
		return story;
	}
	
	
}

