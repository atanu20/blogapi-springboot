package com.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.CommentDto;
import com.blogapi.services.CommentSevice;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentSevice commentSevice;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Long postId) {

		CommentDto createComment = this.commentSevice.createComment(comment, postId);
		
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {

		this.commentSevice.deleteComment(commentId);
		ApiResponse api=new ApiResponse();
		api.setMsg("Comment deleted successfully");
		api.setStatus(true);

		return new ResponseEntity<ApiResponse>( api,HttpStatus.OK);
	}

}
