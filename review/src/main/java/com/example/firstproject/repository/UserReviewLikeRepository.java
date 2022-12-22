package com.example.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.firstproject.entity.UserReviewLikes;

public interface UserReviewLikeRepository extends JpaRepository<UserReviewLikes, String> {
    UserReviewLikes findByUserReviewIdAndUserName(Long userReviewId, String userName);
    void deleteByUserReviewIdAndUserName(Long userReviewId, String userName);
}