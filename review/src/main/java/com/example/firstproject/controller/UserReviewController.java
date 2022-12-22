package com.example.firstproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.example.firstproject.entity.UserReview;
import com.example.firstproject.dto.UserReviewDto;
//import com.example.firstproject.security.UserDetailsImpl;
import com.example.firstproject.service.UserReviewService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserReviewController {

    private final UserReviewService userReviewService;


    @Operation(description = "리뷰 생성, 로그인 필요", method = "POST")
    @PostMapping("/review/{username}")
    public UserReview postUserReview(@RequestPart(name = "review_data") UserReviewDto userReviewDto,
                                     @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                     @PathVariable String username) throws IOException {
        return userReviewService.postUserReview(userReviewDto, multipartFile, username);
    }

    @Operation(description = "리뷰 수정, 로그인 필요", method = "PUT")
    @PutMapping("/reviews/{reviewId}/{username}")
    public UserReview putUserReview(@PathVariable Long reviewId,
                                    @RequestPart(name = "review_data") UserReviewDto userReviewDto,
                                    @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                    @PathVariable String username) throws IOException {
        return userReviewService.putUserReview(reviewId, userReviewDto, multipartFile, username);
    }

    @Operation(description = "리뷰 조회", method = "GET")
    @GetMapping("/reviews/{reviewId}")
    public UserReview getUserReview(@PathVariable Long reviewId) {
        return userReviewService.getUserReview(reviewId);
    }

    @Operation(description = "리뷰 리스트 조회", method = "GET")
    @GetMapping("/reviews") // 타입 별(최근, 좋아요) 순서대로 받아오기
    public List<UserReview> getUserReviews(@RequestParam String sort) throws Exception {
        return userReviewService.getUserReviews(sort);
    }

    @Operation(description = "리뷰 삭제", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}/{username}")
    public void deleteUserReview(@PathVariable Long reviewId, @PathVariable String username) throws AccessDeniedException { // @AuthenticationPrincipal 로그인한 유저 정보 가져오기
        userReviewService.deleteUserReview(reviewId, username);
    }

    @Operation(description = "좋아요 표시, 로그인 필요", method = "POST")
    @PostMapping("/reviews/{reviewId}/like/{username}") // 눌러서 언체크면 삭제하고 아니면 save
    public void userReviewLike(@PathVariable Long reviewId, @PathVariable String username) {
        userReviewService.saveLike(reviewId, username);
    }

    @Operation(description = "좋아요 해제", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}/like/{username}") // 눌러서 언체크면 삭제하고 아니면 save
    public void userReviewUnLike(@PathVariable Long reviewId, @PathVariable String username) {
        userReviewService.deleteLike(reviewId, username);
    }

    @Operation(description = "좋아요 상태 체크", method = "GET")
    @GetMapping("/reviews/{reviewId}/like/{username}") // 좋아요 된 게시물은 나갔다 들어와도 좋아요 된 것으로 표시
    public Map<String, Boolean> getLikeStatus(@PathVariable Long reviewId, @PathVariable String username) {
        return userReviewService.checkLikeStatus(reviewId, username);
    }

    @GetMapping("/reviews/image/{storename}")
    public Resource getImage(@PathVariable String storename) throws MalformedURLException {
        return new UrlResource("file:/" + storename);
    }
}