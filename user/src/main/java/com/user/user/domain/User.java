package com.user.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    public User(UserDto userDto, String profileImgUrl) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.nickname = userDto.getNickname();
        this.profileImgUrl = profileImgUrl;
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.nickname = userDto.getUsername();
        this.profileImgUrl = "http://localhost:5081/image/default.png";
    }

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "USER_ID")
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore // user 조회했을 때 json에서 안 뜨게 함
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImgUrl;

}
