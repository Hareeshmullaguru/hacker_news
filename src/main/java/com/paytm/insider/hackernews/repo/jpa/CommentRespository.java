package com.paytm.insider.hackernews.repo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paytm.insider.hackernews.entity.Comment;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {
    
	public List<Comment> findByStoryId(Long storyId);
	
	@Modifying
	@Query("delete from Comment where storyId= :storyId")
	public void deleteByStoryId(@Param( "storyId" ) Long storyId );
	
}
