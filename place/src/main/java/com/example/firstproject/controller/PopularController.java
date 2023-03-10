package com.example.firstproject.controller;

import com.example.firstproject.dto.LatLngDto;
import com.example.firstproject.dto.PopularDto;
import com.example.firstproject.service.PopularService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.dto.LatLngDto;
import com.example.firstproject.dto.PopularDto;
import com.example.firstproject.service.PopularService;

import java.io.IOException;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
public class PopularController {

    private final PopularService popularService;

    @Operation(description = "테마별 여행지 리스트 조회", method = "GET")
    @GetMapping("/themes")
    public String getPopularPlace() throws IOException{
        return popularService.getRandomType();
    }

    @Operation(description = "테마별 여행지 리스트 필터 적용", method = "POST")
    @PostMapping("/themes")
    public String getPopularPlaceList(@RequestBody PopularDto popularDto) throws IOException {
        return popularService.getPopularPlaceList(popularDto);
    }

    @Operation(description = "테마별 여행지 상세데이터 조회", method = "GET")
    @GetMapping("/themes/{contentId}")
    public String getPopularDetailIntro(@PathVariable Long contentId) throws IOException {
        return popularService.getPopularDetailIntro(contentId);
    }

    @Operation(description = "여행지 위치 기반 날씨 데이터 조회", method = "POST")
    @PostMapping("/weather")
    public String getWeatherPopular(@RequestBody LatLngDto latLngDto) throws IOException {
        return popularService.getWeatherPopular(latLngDto);
    }
}
