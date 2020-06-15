package com.paytm.insider.hackernews.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paytm.insider.hackernews.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long>{

	 public User findByName(String name);
}
