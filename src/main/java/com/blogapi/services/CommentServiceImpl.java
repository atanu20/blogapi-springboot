package com.blogapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Comment;
import com.blogapi.entities.Post;
import com.blogapi.exception.ResourseNotFoundException;
import com.blogapi.payloads.CommentDto;
import com.blogapi.repositories.CommentRepo;
import com.blogapi.repositories.PostRepo;

@Service
public class CommentServiceImpl implements CommentSevice {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourseNotFoundException("Post","Id",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);

	}

	@Override
	public void deleteComment(Long commentId) {
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourseNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);

	}

}
