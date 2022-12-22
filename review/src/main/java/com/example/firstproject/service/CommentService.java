package com.example.firstproject.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.UserReview;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.repository.UserReviewRepository;
//import com.example.firstproject.security.UserDetailsImpl;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;

    private final UserReviewRepository userReviewRepository;

    @Transactional
    public Comment postComment(Long reviewId, CommentDto commentDto, String username) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = new Comment(commentDto, userReview, username);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComment(Long reviewId) {
        return commentRepository.findAllByUserReviewId(reviewId);
    }

    @Transactional
    public Comment updateComment(Long reviewId, Long commentId, CommentDto commentDto, String username) throws AccessDeniedException {
        userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

        if (!username.equals(comment.getUserName())) { // 리뷰 작성자랑 로그인한 유저랑 다르면
            throw new AccessDeniedException("권한이 없습니다.");
        }
        comment.setComment(commentDto.getComment());
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void deleteComment(Long reviewId, Long commentId, String username) throws AccessDeniedException {
        userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

        if (!username.equals(comment.getUserName())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}

