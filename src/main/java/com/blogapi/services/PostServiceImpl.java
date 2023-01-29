package com.blogapi.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Post;
import com.blogapi.entities.User;
import com.blogapi.exception.ResourseNotFoundException;
import com.blogapi.payloads.PostDto;
import com.blogapi.repositories.PostRepo;
import com.blogapi.repositories.UserRepo;

import lombok.extern.slf4j.Slf4j;


@Service
public class PostServiceImpl implements PostService {
	
	

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postdto , Long uid) {
		
		User user = this.userRepo.findById(uid).orElseThrow(()-> new ResourseNotFoundException("User"," Id ",uid));
		
		Post post=this.modelMapper.map(postdto,Post.class);
		post.setImage("default.jpg");
		post.setDate(new Date());
		post.setUser(user);
		
		Post createPost = this.postRepo.save(post);
		return this.modelMapper.map(createPost, PostDto.class);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		
		Post post=this.postRepo.findById(id).orElseThrow(()->new ResourseNotFoundException("Post", "PostId", id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		
		Post save = this.postRepo.save(post);
		
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public PostDto getPostById(Long id) {
		
		Post post=this.postRepo.findById(id).orElseThrow(()->new ResourseNotFoundException("Post", "PostId", id));
		
		PostDto postdato=this.modelMapper.map(post, PostDto.class);
		
		return postdato;
	}

	@Override
	public List<PostDto> getAllPosts() {
		// TODO Auto-generated method stub
		List<Post> findAll = this.postRepo.findAll();
		
//		System.out.println(findAll);
		
		
        List<PostDto> collect = findAll.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return collect ;
		
	}

	@Override
	public void deletePostById( Long pid) {
		// TODO Auto-generated method stub
		
//		User user = this.userRepo.findById(uid).orElseThrow(()-> new ResourseNotFoundException("User"," Id ",uid));
		Post post=this.postRepo.findById (pid).orElseThrow(()->new ResourseNotFoundException("Post", "PostId", pid));
		
		
		
		this.postRepo.delete(post);
		

	}

	@Override
	public List<PostDto> getAllPostsByUser(Long uid) {
		User user = this.userRepo.findById(uid).orElseThrow(()-> new ResourseNotFoundException("User"," Id ",uid));
		List<Post> findByUserPost = this.postRepo.findByUser(user);
		
		List<PostDto> collect = findByUserPost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return collect ;
	}

	@Override
	public List<PostDto> searchPosts(String key) {
		List<Post> data = this.postRepo.findByTitleOrContentContainingIgnoreCase(key,key);
		List<PostDto> collect = data.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deletePostByIdAndUser(Long uid, Long pid) {
		User user = this.userRepo.findById(uid).orElseThrow(()-> new ResourseNotFoundException("User"," Id ",uid));
		Post findByPostIdAndUser = this.postRepo.findByPostIdAndUser(pid, user);
//		System.out.println(findByPostIdAndUser);
		this.postRepo.delete(findByPostIdAndUser);
		
	}

}
