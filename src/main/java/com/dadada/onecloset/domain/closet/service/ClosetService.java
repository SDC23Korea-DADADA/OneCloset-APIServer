package com.dadada.onecloset.domain.closet.service;

import com.dadada.onecloset.domain.closet.dto.ClosetCreateRequestDto;
import com.dadada.onecloset.domain.closet.dto.ClosetEditRequestDto;
import com.dadada.onecloset.domain.closet.dto.ClosetListResponseDto;
import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ClosetService {

    private final ClosetRepository closetRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommonResponse createCloset(ClosetCreateRequestDto requestDto, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId).orElse(null);
        if (user == null)
            return new CommonResponse(400, "잘못된 접근 입니다.");

        Closet closet = Closet
                .builder()
                .user(user)
                .requestDto(requestDto)
                .build();

        closetRepository.save(closet);
        return new CommonResponse(200, "옷장 생성 성공");
    }

    public DataResponse<List<ClosetListResponseDto>> getClosetList(Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId).orElse(null);
        if (user == null)
            return new DataResponse<>(400, "잘못된 접근 입니다.");

        List<Closet> closetList = closetRepository.findByUser(user);
        List<ClosetListResponseDto> responseDtoList = new ArrayList<>();

        for (Closet closet: closetList) {
            ClosetListResponseDto responseDto = ClosetListResponseDto
                    .builder()
                    .closet(closet)
                    .build();
            responseDtoList.add(responseDto);
        }

        return new DataResponse<>(200, "옷장 목록 조회 성공", responseDtoList);
    }

    @Transactional
    public CommonResponse editClosetInfo(ClosetEditRequestDto requestDto, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId).orElse(null);
        Closet closet = closetRepository.findByIdAndUser(requestDto.getClosetId(), user).orElse(null);

        if (user == null || closet == null)
            return new DataResponse<>(400, "잘못된 접근 입니다.");

        closet.editInfo(requestDto);
        return new CommonResponse(200, "옷장 정보 수정 완료");
    }

    @Transactional
    public CommonResponse deleteCloset(Long closetId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId).orElse(null);
        Closet closet = closetRepository.findByIdAndUser(closetId, user).orElse(null);

        if (user == null || closet == null)
            return new DataResponse<>(400, "잘못된 접근 입니다.");

        closetRepository.delete(closet);
        return new CommonResponse(200, "옷장 삭제 완료");
    }

}
