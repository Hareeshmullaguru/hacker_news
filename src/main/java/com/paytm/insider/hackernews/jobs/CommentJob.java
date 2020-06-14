package com.paytm.insider.hackernews.jobs;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.paytm.insider.hackernews.entity.Comment;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.CommentPojo;
import com.paytm.insider.hackernews.repo.jpa.CommentRespository;
import com.paytm.insider.hackernews.util.PairParentChild;

@Component
public class CommentJob implements Runnable{
	
	
	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	@Autowired
	CommentRespository commentRespository;
    
	private List<Long> commentIds;
	private Long parentId;
	
	public List<Long> getCommentIds() {
		return commentIds;
	}


	public void setCommentIds(List<Long> commentIds) {
		this.commentIds = commentIds;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	
	
	
	
	
//	public CommentJob(List<Long> commentIds,Long parentId) {
//		this.commentIds=commentIds;
//		this.parentId=parentId;
//	}
	
	@Transactional
	@Override
	public void run() {
		
      if(commentIds.size() == 0) return;
		
		HashMap<Long,Long> commentsParent=new HashMap<Long,Long>(); // parent comment and count
		
		LinkedList<PairParentChild> kids=new LinkedList<PairParentChild>();
		
		// traverse the commentIds make  childs and add parent  pair
		for(Long comment: commentIds) {
			kids.add(new PairParentChild(comment,comment));
		}
		
		// find all the comments and update the parent comment count
		while(!kids.isEmpty()) {
			
			PairParentChild pair=kids.poll();
			
			commentsParent.put(pair.getParent(), commentsParent.getOrDefault(pair.getParent(), 0L) + 1);
			
			// gets child comments
			CommentPojo commentPojo=hackerApiManager.getCommentInfo(pair.getChild());
			
			if( commentPojo != null  && commentPojo.getKids() != null && commentPojo.getKids().size() > 0) {
				for(Long kid: commentPojo.getKids()) {
					kids.add(new PairParentChild(kid,pair.getParent()));
				}
			}
			
		}
		
		Comparator<PairParentChild> countComparator=(parent,count) -> count.getParent().compareTo(parent.getParent());
		PriorityQueue<PairParentChild> pq=new PriorityQueue<PairParentChild>(countComparator);
		for(Entry<Long,Long> pair: commentsParent.entrySet()) {
			pq.add(new PairParentChild(pair.getKey(),pair.getValue()));
		}
		
		// store the top 10 parent comments
		
		// before inserting remove the top 10 comments of the story id
		if(!pq.isEmpty()) {
			commentRespository.deleteByStoryId(parentId);
		}
		
		int i=0;
		while(i < 10 && !pq.isEmpty()) {
			PairParentChild ppc=pq.poll();
			CommentPojo commentPojo=hackerApiManager.getCommentInfo(ppc.getChild());
			commentRespository.save(convertCommentPojoToComment(commentPojo));
			i++;
		}
		
	}
	
	
	private Comment convertCommentPojoToComment(CommentPojo commentPojo) {
		Comment comment=new Comment();
		comment.setStoryId(commentPojo.getParent());
		comment.setCommentId(commentPojo.getId());
		comment.setAddedBy(commentPojo.getBy());
		Integer age=LocalDateTime.now().getYear() - LocalDateTime.ofEpochSecond(commentPojo.getTime(), 0, ZonedDateTime.now().getOffset()).getYear();
		comment.setTime(age);
		comment.setText(commentPojo.getText());
		return comment;
	}
  
}
