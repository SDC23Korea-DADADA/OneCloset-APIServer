package com.dadada.onecloset.domain.clothes.service;

import com.dadada.onecloset.domain.clothes.dto.response.AdminClothesResponseDto;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;

    public DataResponse<?> getAllClothesList(Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        List<Clothes> clothesList = clothesRepository.findByIsUseData(false);
        List<AdminClothesResponseDto> responseDtoList = new ArrayList<>();
        for (Clothes clothes: clothesList) {
            responseDtoList.add(AdminClothesResponseDto.of(clothes));
        }
        return new DataResponse<>(200, "관리자 의류 조회", responseDtoList);
    }

}
