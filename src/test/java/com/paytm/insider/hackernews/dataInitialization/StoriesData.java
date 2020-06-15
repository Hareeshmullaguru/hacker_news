package com.paytm.insider.hackernews.dataInitialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.insider.hackernews.entity.Story;
import com.paytm.insider.hackernews.entity.User;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;

public class StoriesData {

    public static List<Story> getTopTenStories(){
		
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Story>> typeReference = new TypeReference<List<Story>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/stories.json");
		try {
			    List<Story> stories = mapper.readValue(inputStream,typeReference);
				return stories;
			} catch (IOException e){
				System.out.println("Unable to get stories: " + e.getMessage());
		    }
	  return null;
	}
    
    public static StoryPojo getStoryInfo(Long storyId){
    	
    	ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<StoryPojo>> typeReference = new TypeReference<List<StoryPojo>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/storyPojo.json");
		try {
			    List<StoryPojo> stories = mapper.readValue(inputStream,typeReference);
			    for(StoryPojo storyPojo: stories) {
			    	if(storyPojo.getId() == storyId) {
			    		return storyPojo;
			    	}
			    }
				return null;
			} catch (IOException e){
				System.out.println("Unable to get story info: " + e.getMessage());
		    }
	  return null;
    }
}
