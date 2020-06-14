package com.paytm.insider.hackernews.repo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paytm.insider.hackernews.entity.Comment;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {
    
	public List<Comment> findByStoryId(Long storyId);
	
	public void deleteByStoryId(Long storyId);
	
}
