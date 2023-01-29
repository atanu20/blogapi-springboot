package com.blogapi.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blogapi.entities.Comment;
import com.blogapi.entities.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostDto {
	private Long postId;
	private String title;
	private String content;
	private String image;
    private Date date;
	
	
	private UserDto user;
	
	private List<CommentDto> comments=new ArrayList<>();
	
}
