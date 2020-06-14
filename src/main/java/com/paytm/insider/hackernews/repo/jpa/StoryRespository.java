package com.paytm.insider.hackernews.repo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paytm.insider.hackernews.entity.Story;

@Repository
public interface StoryRespository extends JpaRepository<Story, Long>{

	  @Query("select r from Story r where r.isTopStory = :isTopStory order by createdAt desc")
	  public List<Story> getOldOrNewStories( @Param( "isTopStory" ) Boolean isTopStory);
	  
	  @Modifying
	  @Query("delete  Story  where isTopStory = FALSE")
	  public void deleteOldStories();
	  
	  @Modifying
	  @Query("update Story set isTopStory=FALSE where isTopStory=TRUE")
	  public void changeNewToOldStories();
	  
}
