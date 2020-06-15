package com.paytm.insider.hackernews.external.hackerNews;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.paytm.insider.hackernews.external.RestApiManager;
import com.paytm.insider.hackernews.external.endpoints.HackerEndpoints;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.external.pojo.hackernews.UserPojo;

@Component
public class HackerNewsApiManager extends RestApiManager {

	@Value( "${external.hackernews.base.url}" )
    private String baseHackerUrl;
	
    private static final Logger logger = LoggerFactory.getLogger( HackerNewsApiManager.class );
    
    public List getTopStories() {
        HttpHeaders httpHeaders = getRequestHeaders();
        return super.get(
        	baseHackerUrl,
            HackerEndpoints.top_stories_url,
            null,
            httpHeaders,
            List.class,
            null
        );
    }
    
    public StoryPojo getStoryInfo(Long storyId) {
    	HttpHeaders httpHeaders = getRequestHeaders();
        return super.get(
        	baseHackerUrl,
            HackerEndpoints.get_story_comment_url+storyId+".json",
            null,
            httpHeaders,
            StoryPojo.class,
            null
        );
    }
    
    public CommentPojo getCommentInfo(Long storyId) {
    	HttpHeaders httpHeaders = getRequestHeaders();
        return super.get(
        	baseHackerUrl,
            HackerEndpoints.get_story_comment_url+storyId+".json",
            null,
            httpHeaders,
            CommentPojo.class,
            null
        );
    }
    
    public UserPojo getUserInfo(String name) {
    	HttpHeaders httpHeaders = getRequestHeaders();
        return super.get(
        	baseHackerUrl,
            HackerEndpoints.get_user_url+name+".json",
            null,
            httpHeaders,
            UserPojo.class,
            null
        );
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( new MediaType( "application", "json" ) );
        return requestHeaders;
    }
}
    
