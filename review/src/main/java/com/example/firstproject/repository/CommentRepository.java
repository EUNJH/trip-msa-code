package com.example.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.firstproject.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserReviewId(Long reviewId);
}
