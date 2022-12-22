package com.example.firstproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.dto.CommentDto;
//import com.example.firstproject.security.UserDetailsImpl;
import com.example.firstproject.service.CommentService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(description = "댓글 생성, 로그인 필요", method = "POST")
    @PostMapping("/reviews/{reviewId}/comment/{username}") // 댓글 작성
    public Comment postComment(@PathVariable Long reviewId, @RequestBody CommentDto comment,
                               @PathVariable String username) {
        return commentService.postComment(reviewId, comment, username);
    }

    @Operation(description = "댓글 조회", method = "GET")
    @GetMapping("/reviews/{reviewId}/comments") // 댓글 보여주기
    public List<Comment> getComment(@PathVariable Long reviewId) {
        return commentService.getComment(reviewId);
    }

    @Operation(description = "댓글 수정, 로그인 필요", method = "PUT")
    @PutMapping("/reviews/{reviewId}/comments/{commentId}/{username}") // 댓글 수정하기
    public Comment updateComment(@PathVariable Long commentId, @PathVariable Long reviewId, @RequestBody CommentDto comment,
                                 @PathVariable String username) throws AccessDeniedException {
        return commentService.updateComment(reviewId, commentId, comment, username);
    }

    @Operation(description = "댓글 삭제, 로그인 필요", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}/comments/{commentId}/{username}") // 댓글 삭제하기
    public void deleteComment(@PathVariable Long commentId, @PathVariable Long reviewId,
                              @PathVariable String username) throws AccessDeniedException {
        commentService.deleteComment(reviewId, commentId, username);
    }
}

