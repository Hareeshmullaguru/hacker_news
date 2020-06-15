package com.paytm.insider.hackernews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paytm.insider.hackernews.entity.User;
import com.paytm.insider.hackernews.external.hackerNews.HackerNewsApiManager;
import com.paytm.insider.hackernews.external.pojo.hackernews.UserPojo;
import com.paytm.insider.hackernews.repo.jpa.UserRespository;
import com.paytm.insider.hackernews.util.UnixTimeStampToYear;

@Service
public class UserService {

	@Autowired
	UserRespository userRespository;
	
	@Autowired
	HackerNewsApiManager hackerApiManager;
	
	public User getUser(String name) {
		User user=userRespository.findByName(name);
		if(user == null) {
			UserPojo userPojo=hackerApiManager.getUserInfo(name);
			user = convertUserPojoToUser(userPojo);
			userRespository.save(user);
		} 
		return user;
	}
	
	public void saveUser(UserPojo userPojo) {
		getUser(userPojo.getId());
		
	}
	
	public User convertUserPojoToUser(UserPojo userPojo) {
		User user=new User();
		user.setName(userPojo.getId());
		user.setAgeofProfile(UnixTimeStampToYear.getAgeFromUnixTimeStamp(userPojo.getCreated()));
		return user;
	}
}
