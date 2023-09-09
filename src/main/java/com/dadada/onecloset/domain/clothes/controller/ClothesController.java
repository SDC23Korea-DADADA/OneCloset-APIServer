package com.dadada.onecloset.domain.clothes.controller;

import com.dadada.onecloset.domain.clothes.dto.ClothesAnalyzeResponseDto;
import com.dadada.onecloset.domain.clothes.dto.ClothesRegistRequestDto;
import com.dadada.onecloset.domain.clothes.service.ClothesService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public CommonResponse registClothes(HttpServletRequest request, @ModelAttribute ClothesRegistRequestDto requestDto) throws IOException {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return clothesService.registClothes(requestDto, userId);
    }

//    @PostMapping("/check")
//    public DataResponse<byte[]> clothesCheck(@RequestParam("image") MultipartFile multipartFile) throws IOException {
//        byte[] fileBytes = multipartFile.getBytes();
//        return new DataResponse<>(200,"의류 여부", fileBytes);
////        return new ResponseEntity<>(multipartFile, HttpStatus.OK);
//    }
}
