package com.dadada.onecloset.domain.clothes.controller;

import com.dadada.onecloset.domain.clothes.dto.*;
import com.dadada.onecloset.domain.clothes.service.ClothesService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clothes")
public class ClothesController {

    private final JwtUtil jwtUtil;
    private final ClothesService clothesService;

    @PostMapping("/check")
    public DataResponse<Boolean> checkClothes(@RequestParam("image") MultipartFile multipartFile) {
        return clothesService.checkClothes(multipartFile);
    }

    @PostMapping("/info")
    public DataResponse<ClothesAnalyzeResponseDto> analyzeClothes(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return clothesService.analyzeClothes(multipartFile);
    }

    @PostMapping
    public CommonResponse registClothes(HttpServletRequest request, @ModelAttribute ClothesRegistRequestDto requestDto) throws Exception {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.registClothes(requestDto, userId);
    }

    @GetMapping("/hashtag")
    public DataResponse<List<String>> getHashtagList(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.getHashtagList(userId);
    }

    // 페이징처리 추가하기
    @GetMapping("/list")
    public DataResponse<List<ClothesListResponseDto>> getClothesListInDefaultCloset(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.getClothesListInDefaultCloset(userId);
    }

    // 페이징처리 추가하기
    @GetMapping("/list/{id}")
    public DataResponse<List<ClothesListResponseDto>> getClothesListInCustomCloset(HttpServletRequest request, @PathVariable("id") Long closetId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.getClothesListInCustomCloset(closetId, userId);
    }

    @GetMapping("/{id}")
    public DataResponse<ClothesDetailResponseDto> getClothesDetail(HttpServletRequest request, @PathVariable("id") Long clothesId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.getClothesDetail(clothesId, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteClothes(HttpServletRequest request, @PathVariable("id") Long clothesId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.deleteClothes(clothesId, userId);
    }

    @PutMapping
    public CommonResponse updateClothes(HttpServletRequest request, @ModelAttribute ClothesUpdateRequestDto requestDto) throws IOException {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.updateClothes(requestDto, userId);
    }

    @PutMapping("/temp/{id}")
    public CommonResponse restoreClothes(HttpServletRequest request, @PathVariable("id") Long clothesId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.restoreClothes(clothesId, userId);
    }
}
