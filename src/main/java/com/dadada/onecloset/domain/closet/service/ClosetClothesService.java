package com.dadada.onecloset.domain.closet.service;

import com.dadada.onecloset.domain.closet.dto.request.ClosetClothesRequestDto;
import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import com.dadada.onecloset.domain.closet.repository.ClosetClothesRepository;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ClosetClothesService {

    private final UserRepository userRepository;
    private final ClosetRepository closetRepository;
    private final ClothesRepository clothesRepository;
    private final ClosetClothesRepository closetClothesRepository;

    @Transactional
    public CommonResponse registClothesToCloset(ClosetClothesRequestDto requestDto, Long userId) {
        User user = getUser(userId);
        Closet closet = closetRepository.findByIdAndUser(requestDto.getClosetId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOSET_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUserWhereIsRegistIsTrue(requestDto.getClothesId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));

        ClosetClothes closetClothes = ClosetClothes
                .builder()
                .closet(closet)
                .clothes(clothes)
                .build();
        closetClothesRepository.save(closetClothes);

        return new CommonResponse(200, "등록 성공");
    }

    @Transactional
    public CommonResponse deleteClothesToCloset(ClosetClothesRequestDto requestDto, Long userId) {
        User user = getUser(userId);
        Closet closet = closetRepository.findByIdAndUser(requestDto.getClosetId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOSET_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUserWhereIsRegistIsTrue(requestDto.getClothesId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        ClosetClothes closetClothes = closetClothesRepository.findByClosetAndClothes(closet, clothes)
                .orElseThrow();

        closetClothesRepository.delete(closetClothes);

        return new CommonResponse(200, "삭제 완료");
    }

    public User getUser(Long userId) {
        return userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }
}
