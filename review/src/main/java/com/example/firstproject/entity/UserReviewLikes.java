package com.example.firstproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReviewLikes extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "USER_REVIEW_LIKES_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_REVIEW_ID", nullable = false)
    private UserReview userReview;


    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    public UserReviewLikes(UserReview userReview, String userName) {
        this.userReview = userReview;
        this.userName = userName;
    }
}