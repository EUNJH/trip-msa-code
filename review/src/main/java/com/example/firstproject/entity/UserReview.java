package com.example.firstproject.entity;

import com.example.firstproject.dto.UserReviewDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReview extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column
    private String title;

    @Column
    private String place;

    @Column
    private String review;

    @Column
    private String reviewImgUrl;

    @Column
    private int likeCnt;

    @Column(name="USER_NAME")
    private String username;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<UserReviewLikes> userReviewLikes;

    public UserReview(UserReviewDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.place = requestDto.getPlace();
        this.review = requestDto.getReview();
        this.username = username;
    }

}