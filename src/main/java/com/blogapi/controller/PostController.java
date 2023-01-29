package com.blogapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.blogapi.payloads.PostDto;
import com.blogapi.services.FileService;
import com.blogapi.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{uid}/post")
	public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto , @PathVariable Long uid){
		
		PostDto createPost = this.postService.createPost(postDto, uid);
		
		return new ResponseEntity<PostDto> (createPost,HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/user/{uid}/post")
	public ResponseEntity<?> getPostByUserId(@PathVariable Long uid){
		
		 List<PostDto> allPostsByUser = this.postService.getAllPostsByUser(uid);
		
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("data", allPostsByUser);
		return  ResponseEntity.ok(map);
		
		
	}
	
	@DeleteMapping("/user/{uid}/post/{pid}")
	public ResponseEntity<?> deletePostByIds(@PathVariable Long uid , @PathVariable Long pid){
		
		 this.postService.deletePostByIdAndUser(uid, pid);
		
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("data", "delete done");
		return  ResponseEntity.ok(map);
		
		
	}
	
	
	
	
	
	@GetMapping("/post/{pid}")
	public ResponseEntity<?> getPostById(@PathVariable Long pid){
		
		PostDto postById = this.postService.getPostById(pid);
		
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("data", postById);
		return  ResponseEntity.ok(map);
		
		
	}
	
	@DeleteMapping("/post/{pid}")
	public ResponseEntity<?> deletePostById( @PathVariable Long pid){
		
		 this.postService.deletePostById( pid);
		
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("msg","Post deleted successfully done.");
		  
		return  ResponseEntity.ok(map);
		
		
	}
	
	@PutMapping("/post/{pid}")
	public ResponseEntity<?> updatePostById(@RequestBody PostDto postDto ,@PathVariable Long pid)
	{
		PostDto updatePost = this.postService.updatePost(postDto, pid);
		Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("msg","Post Updated successfully done.");
		   map.put("data",updatePost);
		   
		   return new ResponseEntity<>(map,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<?> getallposts(){
		List<PostDto> allPosts = this.postService.getAllPosts();
	
		Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   
		   map.put("data",allPosts);
		   
		   return new ResponseEntity<>(map,HttpStatus.OK);
		
	}
	
	@GetMapping("/post/search/{key}")
	public ResponseEntity<?> getallposts(@PathVariable String key){
		List<PostDto> allPosts = this.postService.searchPosts(key);
	
		Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   
		   map.put("data",allPosts);
		   
		   return new ResponseEntity<>(map,HttpStatus.OK);
		
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Long postId) throws IOException{
		
		PostDto postById = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postById.setImage(fileName);
		PostDto updatePost = this.postService.updatePost(postById, postId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("msg","Post Updated successfully done.");
		   map.put("data",updatePost);
		   
		   return new ResponseEntity<>(map,HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());  ;
//        http://localhost:9000/api/post/image/1bae6331-d681-452a-b3b8-d6677b259bac.png
    }
	
	
	
	
}
