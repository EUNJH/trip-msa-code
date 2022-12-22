package com.user.user.controller;

import com.user.user.domain.User;
import com.user.user.dto.ResponseDto;
import com.user.user.dto.UserDto;
import com.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Value("${TOKEN}")
    String token;

    @Autowired
    public UserService userService;

    @PostMapping("/user/signup")
    public String createUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto); // 사용자 등록
        return "회원가입을 축하드립니다!";
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        if (!userService.login(userDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(new ResponseDto(userDto.getUsername(), token));
    }

    @PostMapping("/user/signup/check")
    public String signCheck(@RequestBody UserDto userDto) {
        JSONObject response = new JSONObject();
        try {
            userService.check(userDto);
        } catch (IllegalArgumentException e) {
            response.put("exists", Boolean.TRUE);
            return response.toString();
        }
        response.put("exists", Boolean.FALSE);
        return response.toString();
    }

    @PostMapping("/user")
    public User updateProfile(@RequestPart(required = false) String nickname,
                              @RequestPart(name = "profileImgUrl", required = false) MultipartFile multipartFile,
                              @RequestPart (required = false) String username) throws IOException {
        return userService.updateProfile(nickname, multipartFile, username);
    }

    @DeleteMapping("/user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @GetMapping("/image/{storeName}")
    public Resource getProfile(@PathVariable String storeName) throws MalformedURLException {
        return new UrlResource("file:/" + storeName);
    }

    @GetMapping("/user/{username}")
    public User getUserInfo(@PathVariable String username) {
        return userService.getUser(username);
    }

}
