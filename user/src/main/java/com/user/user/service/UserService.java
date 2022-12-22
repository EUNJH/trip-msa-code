package com.user.user.service;

import com.user.user.domain.User;
import com.user.user.dto.ResponseDto;
import com.user.user.dto.UserDto;
import com.user.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${TOKEN}")
    String token;

    @Value("${fileDir}")
    String fileDir;

    String url = "http://localhost:5081/image/";

    @Transactional
    public void registerUser(UserDto userDto) {
        User user = new User(userDto);
        userRepository.save(user);
    }

    public boolean login(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if (Objects.equals(user, Optional.empty()) || !(user.get().getPassword().equals(userDto.getPassword()))) {
            return false;
        }
        return true;
    }

    public void check(UserDto userDto) {
        Optional<User> found = userRepository.findByUsername(userDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get();
    }


    @Transactional
    public User updateProfile(String nickname, MultipartFile multipartFile, String username) throws IOException {
        Optional<User> user = userRepository.findByUsername(username);
        user.get().setNickname(nickname);
        String storeFilename = storeFile(multipartFile);
        user.get().setProfileImgUrl(url + storeFilename);
        return userRepository.save(user.get());
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = originalFilename(multipartFile);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;
    }

    public String  originalFilename(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
