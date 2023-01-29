package com.blogapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
