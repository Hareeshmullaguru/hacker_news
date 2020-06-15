package com.paytm.insider.hackernews.dataInitialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;

public class ComentsData {

	 public static List<Comment> getTopTenComments(Long storyId){
		 
		 if(storyId != 23489068L) return null;
		 
		 ObjectMapper mapper = new ObjectMapper();
		 TypeReference<List<Comment>> typeReference = new TypeReference<List<Comment>>(){};
		 InputStream inputStream = TypeReference.class.getResourceAsStream("/json/comments.json");
		 try {
			    List<Comment> comments = mapper.readValue(inputStream,typeReference);
				return comments;
			} catch (IOException e){
				System.out.println("Unable to get comments: " + e.getMessage());
		    }
		 return null;
	 }
	 
	 public static CommentPojo getComment(Long commentId) {
		 if(commentId != 41L) return null;
		 
		 ObjectMapper mapper = new ObjectMapper();
		 TypeReference<CommentPojo> typeReference = new TypeReference<CommentPojo>(){};
		 InputStream inputStream = TypeReference.class.getResourceAsStream("/json/41_comment.json");
		 try {
			    CommentPojo comment = mapper.readValue(inputStream,typeReference);
				return comment;
			} catch (IOException e){
				System.out.println("Unable to get comment: " + e.getMessage());
		    }
		 return null;
		 
	 }
}