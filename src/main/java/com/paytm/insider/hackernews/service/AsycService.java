package com.paytm.insider.hackernews.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.entity.Story;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.jobs.CommentJob;
import com.paytm.insider.hackernews.util.UnixTimeToLocalDateTIme;

@Service
public class AsycService {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	StoryService storyService;
	
	
	@Autowired
	CommentJob commentJob;
	
	@Autowired
	CommentJob commentJob2;
	
	@Autowired
	CommentJob commentJob3;
	
	@Value("${hackernews.topstories}")
	private Integer top;
	
	
	
	@Scheduled(fixedRate = 600000)  // every 10 minutes run this job (1000 * 60) * 10
	@Transactional
	public void findTopStories(){
		
		// get the top 500 stories from the hacker news api
        List<Integer> topStoires=storyService.getTopStories();
		
       // System.out.println(" top stories " + topStoires);
        
        
	
        // check the size of the return list
        if(topStoires.size() > 0) {
        	
        	
        	Comparator<StoryPojo> scoreComparator=(storyScores1,storyScores2) -> storyScores2.getScore().compareTo(storyScores1.getScore());
        	PriorityQueue<StoryPojo> storyScores=new PriorityQueue<StoryPojo>(scoreComparator);
        	for(Integer storyId: topStoires) {
        		// find the score of each operator
        		StoryPojo storyPojo=storyService.getStoryInfo(storyId.longValue());
        		if(storyPojo != null) {
        			storyScores.add(storyPojo);
        		}
        		
    		}
        	
        	// if returned list not null
        	if(storyScores.size() > 0) {
        		
        		// remove the comments of the top stories
        		commentService.deleteAllComments();
        		
        		// remove the old stories
        		storyService.deleteOldStories();
        		
        		// change the new to old stories
        		storyService.changeNewToOldStories();;
        		
        		// update the top stories into 
        		int i=top;
        		while(i <= 10 && !storyScores.isEmpty()) {
        			StoryPojo storyPojo=storyScores.poll(); // get the top story
        			Story story=convertStoryPojotoStory(storyPojo); // convert into data object format
        			storyService.saveStory(story); // store into data base
        			//Runnable commentJob=new CommentJob(storyPojo.getKids(),story.getStoryId()); // find the top 10 comments for this story store into DB
        			// only two threads for comment
        			
        			if(i == 1) {
        				commentJob.setCommentIds(storyPojo.getKids());
                		commentJob.setParentId(story.getStoryId());
                		new Thread(commentJob).start();
        			}
        			else if(i == 2) {
        				commentJob2.setCommentIds(storyPojo.getKids());
                		commentJob2.setParentId(story.getStoryId());
                		new Thread(commentJob2).start();
        			}
        			else if(i == 3) {
        				commentJob.setCommentIds(storyPojo.getKids());
                		commentJob.setParentId(story.getStoryId());
                		new Thread(commentJob3).start(); // execute the parallel thread
        			}
        			
        			i++;
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
		story.setCreatedAt(UnixTimeToLocalDateTIme.getLocalDateTime(storyPojo.getTime()));
		story.setIsTopStory(true);
		story.setSubmittedBy(storyPojo.getBy());
		story.setUrl(storyPojo.getUrl());
		story.setScore(storyPojo.getScore());
		
		return story;
	}
	
	
}

