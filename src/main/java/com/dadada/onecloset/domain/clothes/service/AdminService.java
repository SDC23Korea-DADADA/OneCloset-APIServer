package com.dadada.onecloset.domain.clothes.service;

import com.dadada.onecloset.domain.clothes.dto.response.AdminClothesResponseDto;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.entity.type.Role;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;

    public DataResponse<?> getAllClothesList(Long userId) {
        User user = userRepository.findByIdWhereRoleIsAdmin(userId, Role.ADMIN)
                .orElseThrow(() -> new CustomException(ExceptionType.NOT_ADMIN));

        List<Clothes> clothesList = clothesRepository.findByIsUseData(false);
        List<AdminClothesResponseDto> responseDtoList = new ArrayList<>();

        for (Clothes clothes: clothesList) {
            responseDtoList.add(AdminClothesResponseDto.of(clothes));
        }

        return new DataResponse<>(200, "관리자 의류 조회", responseDtoList);
    }

    @Transactional
    public CommonResponse switchIsTraining(Long clothesId, Long userId) {
        User user = userRepository.findByIdWhereRoleIsAdmin(userId, Role.ADMIN)
                .orElseThrow(() -> new CustomException(ExceptionType.NOT_ADMIN));
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        clothes.switchIsTraing();
        return new CommonResponse(200, "학습여부 변경 완료");
    }

}
