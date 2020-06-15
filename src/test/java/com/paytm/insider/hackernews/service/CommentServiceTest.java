package com.paytm.insider.hackernews.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.paytm.insider.hackernews.constant.InputParams;
import com.paytm.insider.hackernews.dataInitialization.ComentsData;
import com.paytm.insider.hackernews.dataInitialization.StoriesData;
import com.paytm.insider.hackernews.dataInitialization.UsersData;
import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
import com.paytm.insider.hackernews.external.pojo.hackernews.StoryPojo;
import com.paytm.insider.hackernews.jobs.CommentJob;
import com.paytm.insider.hackernews.repo.jpa.CommentRespository;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {
	
	@InjectMocks
	private CommentService commentService;
	
	@Mock
	private HackerNewsApiManager hackerApiManager;
	
	@Mock
	private CommentRespository commentRespository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private StoryService storyService;
	
	@Mock
	private CommentJob commentJob;
	
	List<Comment> topComments;
	
	StoryPojo storyPojo;
	
	@Before
	public void init() {
		topComments = ComentsData.getTopTenComments(InputParams.storyId);
		storyPojo = StoriesData.getStoryInfo(InputParams.storyId);
	}
	
	@Test
	public void topTenCommentsofStory() {
		when(commentRespository.findByStoryId(InputParams.storyId)).thenReturn(topComments);
		//when(storyService.getStoryInfo(23489068L)).thenReturn(storyPojo);
		when(userService.getUser(Mockito.anyString()))
		 .thenAnswer(new Answer() {
		      @Override
		      public Object answer(InvocationOnMock invocation) throws Throwable {
			      Object[] args = invocation.getArguments();
		          return UsersData.getUser((String)args[0]);
		      }
		});
		
		Integer actualCount=commentService.topTenCommentsofStory(InputParams.storyId).size();
		
		assertEquals(10,actualCount);
	}
	
	
	@Test
	public void getCommentInfoTest() {
		when(hackerApiManager.getCommentInfo(InputParams.commentId)).thenReturn(ComentsData.getComment(InputParams.commentId));
		CommentPojo comment=commentService.getCommentInfo(InputParams.commentId);
		assertEquals(InputParams.commentId,comment.getId());
	}
	
	@Test
	public void deleteCommentByStoryIdTest() {
		doNothing().when(commentRespository).deleteByStoryId(InputParams.storyId);
		commentService.deleteCommentByStoryId(InputParams.storyId);
		verify(commentRespository).deleteByStoryId(InputParams.storyId);
	}
	
	@Test
	public void saveCommentTest() {
		when(commentRespository.save(any(Comment.class))).thenReturn(null);
		commentService.saveComment(new Comment());
		verify(commentRespository).save(Mockito.any());
	}
	
	@Test
	public void deleteAllCommentsTest() {
		doNothing().when(commentRespository).deleteAll();
		commentService.deleteAllComments();
		verify(commentRespository).deleteAll();
	}
	
}
