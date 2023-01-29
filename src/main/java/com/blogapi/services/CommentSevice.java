package com.blogapi.services;

import com.blogapi.payloads.CommentDto;

public interface CommentSevice {
	CommentDto createComment(CommentDto commentDto, Long postId);

	void deleteComment(Long commentId);
}
