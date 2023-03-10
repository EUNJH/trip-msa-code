package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_REVIEW_ID", nullable = false)
    private UserReview userReview;

    @Column(name="USER_NAME")
    private String userName;

    public Comment(CommentDto commentDto, UserReview userReview, String userName) {
        this.comment = commentDto.getComment();
        this.userReview = userReview;
        this.userName = userName;
    }
}