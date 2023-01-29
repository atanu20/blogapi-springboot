package com.blogapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapi.entities.Post;
import com.blogapi.entities.User;
import com.blogapi.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByUser(User user);
	Post findByPostIdAndUser(Long postId, User user);
	List<Post> findByTitleOrContentContainingIgnoreCase(String title,String content);
	
//	@Query(value="select * from posts where post_id=? and user_id=?",nativeQuery = true)
//	Post postbyidanduserid(Long pid,Long uid);

	
}
