package com.blogapi.services;

import java.util.List;

import com.blogapi.payloads.PostDto;



public interface PostService {
	
	PostDto createPost(PostDto postDto,Long uid);
	PostDto updatePost(PostDto postDto, Long id);	
	PostDto getPostById(Long id);
	List<PostDto> getAllPosts();
	void deletePostById( Long pid);
	void deletePostByIdAndUser(Long uid, Long pid);
	
	List<PostDto> getAllPostsByUser(Long id);
	List<PostDto> searchPosts(String key);

}
