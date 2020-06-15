package com.paytm.insider.hackernews.dataInitialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.insider.hackernews.entity.User;

public class UsersData {

	public static List<User> getUsers(){
		
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
		try {
			    List<User> users = mapper.readValue(inputStream,typeReference);
				return users;
			} catch (IOException e){
				System.out.println("Unable to get users: " + e.getMessage());
		    }
	  return null;
	}
	
	public static User getUser(String name) {
		
		List<User> users = getUsers();
		
		for(User user: users) {
			
			if(user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}
}
