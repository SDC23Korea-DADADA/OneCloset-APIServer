package com.dadada.onecloset.domain.clothes.controller;

import com.dadada.onecloset.domain.clothes.dto.request.ClothesRegistRequestDto;
import com.dadada.onecloset.domain.clothes.dto.request.ClothesUpdateRequestDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClotheaCareSolutionResponseDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesAnalyzeResponseDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesDetailResponseDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesListResponseDto;
import com.dadada.onecloset.domain.clothes.service.ClothesService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clothes")
public class ClothesController {

    private final ClothesService clothesService;

    @PostMapping("/check")
    public DataResponse<Boolean> checkClothes(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return clothesService.checkClothes(multipartFile);
    }

    @PostMapping("/info")
    public DataResponse<ClothesAnalyzeResponseDto> analyzeClothes(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return clothesService.analyzeClothes(multipartFile);
    }

    @PostMapping
    public DataResponse<Long> registClothes(Principal principal, @ModelAttribute ClothesRegistRequestDto requestDto) throws Exception {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.registClothes(requestDto, userId);
    }

    @GetMapping("/hashtag")
    public DataResponse<List<String>> getHashtagList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.getHashtagList(userId);
    }

    // 페이징처리 추가하기
    @GetMapping("/list")
    public DataResponse<List<ClothesListResponseDto>> getClothesListInDefaultCloset(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.getClothesListInDefaultCloset(userId);
    }

    // 페이징처리 추가하기
    @GetMapping("/list/{id}")
    public DataResponse<List<ClothesListResponseDto>> getClothesListInCustomCloset(Principal principal, @PathVariable("id") Long closetId) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.getClothesListInCustomCloset(closetId, userId);
    }

    @GetMapping("/{id}")
    public DataResponse<ClothesDetailResponseDto> getClothesDetail(Principal principal, @PathVariable("id") Long clothesId) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.getClothesDetail(clothesId, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteClothes(Principal principal, @PathVariable("id") Long clothesId) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.deleteClothes(clothesId, userId);
    }

    @PutMapping
    public CommonResponse updateClothes(Principal principal, @ModelAttribute ClothesUpdateRequestDto requestDto) throws IOException {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.updateClothes(requestDto, userId);
    }

    @PutMapping("/temp/{id}")
    public CommonResponse restoreClothes(Principal principal, @PathVariable("id") Long clothesId) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.restoreClothes(clothesId, userId);
    }

    @GetMapping("/material/{material}")
    public DataResponse<ClotheaCareSolutionResponseDto> getClothesCareSolutionByMaterial(@PathVariable("material") String material) {
        return clothesService.getClothesCareSolutionByMaterial(material);
    }

    // 의류DB에서 삭제
    @DeleteMapping("/temp/delete/{id}")
    public CommonResponse tempDeleteClothes(Principal principal, @PathVariable("id") Long clothesId) {
        Long userId = Long.parseLong(principal.getName());
        return clothesService.tempDeleteClothes(clothesId, userId);
    }
}
