package com.example.firstproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.firstproject.entity.UserReview;
import com.example.firstproject.entity.UserReviewLikes;
import com.example.firstproject.dto.UserReviewDto;

import com.example.firstproject.repository.UserReviewLikeRepository;
import com.example.firstproject.repository.UserReviewRepository;


import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    private final UserReviewLikeRepository userReviewLikeRepository;

    String fileDir = "/";
    String fileUrl = "http://localhost:8080/reviews/image/";


    @Transactional
    public UserReview putUserReview(Long reviewId, UserReviewDto userReviewRequestDto, MultipartFile multipartFile, String username) throws IOException {
        UserReview editReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
        editReview.setTitle(userReviewRequestDto.getTitle());
        editReview.setPlace(userReviewRequestDto.getPlace());
        editReview.setReview(userReviewRequestDto.getReview());

        if (!username.equals(getUserReview(reviewId).getUsername())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (!multipartFile.isEmpty()) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            fileUrl = fileUrl + storeFile(multipartFile);
        } else { // 사진 선택하면
            fileUrl = fileUrl + "default.png";
        }
        editReview.setReviewImgUrl(fileUrl);
        return userReviewRepository.save(editReview);
    }

    @Transactional
    public UserReview postUserReview(UserReviewDto userReviewDto, MultipartFile multipartFile, String username) throws IOException {


        UserReview userReview = new UserReview(userReviewDto, username);


        if (!multipartFile.isEmpty()) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            fileUrl = fileUrl + storeFile(multipartFile);
        } else { // 사진 선택하면
            fileUrl = fileUrl + "default.png";
        }
        userReview.setReviewImgUrl(fileUrl);
        return userReviewRepository.save(userReview);
    }

    public UserReview getUserReview(Long reviewId) {


        return userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    @Transactional
    public void deleteUserReview(Long reviewId, String username) throws AccessDeniedException {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        if (!username.equals(getUserReview(reviewId).getUsername())) { // 리뷰 작성자랑 로그인한 유저랑 다르면
            throw new AccessDeniedException("권한이 없습니다.");
        }

        String reviewImgUrl = userReview.getReviewImgUrl(); // userReview에서 이미지 url 가져옴

        userReviewRepository.deleteById(reviewId); // DB에서 리뷰 삭제
    }

    @Transactional
    public void deleteLike(Long reviewId, String username) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );

        userReview.setLikeCnt(userReview.getLikeCnt() - 1);
        userReviewLikeRepository.deleteByUserReviewIdAndUserName(reviewId, username);
    }

    @Transactional
    public UserReviewLikes saveLike(Long reviewId, String username) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, username);
        userReview.setLikeCnt(userReview.getLikeCnt() + 1);
        userReviewLikeRepository.save(userReviewLikes);
        return userReviewLikes;
    }

    public Map<String, Boolean> checkLikeStatus(Long reviewId, String username) {
        Map<String, Boolean> response = new HashMap<>();
        UserReviewLikes userReviewLikes = userReviewLikeRepository.findByUserReviewIdAndUserName(reviewId, username);  //변수타입 바껴지면 함수이름도 바꿔줘야함
        if (userReviewLikes == null) {
            response.put("likeStatus", Boolean.FALSE);
        } else {
            response.put("likeStatus", Boolean.TRUE);
        }
        return response;
    }

    public List<UserReview> getUserReviews(String type) throws Exception {
        if (type.equals("like")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "likeCnt", "CreatedAt"));
        } else if (type.equals("date")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "CreatedAt"));
        } else {
            throw new Exception();
        }
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