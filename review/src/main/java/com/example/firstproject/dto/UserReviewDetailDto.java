package com.example.firstproject.dto;

import com.example.firstproject.entity.UserReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewDetailDto {

    private String title;
    private String place;
    private String review;
    private String filename;
    private String filepath;
    private String nickname;
    private String profileUrl;

    public UserReviewDetailDto(UserReview userReview, String nickname, String profileUrl) {
        this.title = userReview.getTitle();
        this.place = userReview.getPlace();
        this.review = userReview.getReview();
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
