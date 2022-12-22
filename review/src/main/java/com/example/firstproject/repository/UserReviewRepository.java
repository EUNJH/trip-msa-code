package com.example.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.firstproject.entity.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}