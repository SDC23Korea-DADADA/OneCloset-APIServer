package com.dadada.onecloset.domain.closet.service;

import com.dadada.onecloset.domain.closet.dto.ClosetCreateRequestDto;
import com.dadada.onecloset.domain.closet.dto.ClosetListResponseDto;
import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
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
    public void createCloset(ClosetCreateRequestDto requestDto, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Closet closet = Closet
                .builder()
                .user(user)
                .requestDto(requestDto)
                .build();

        closetRepository.save(closet);
    }

    public List<ClosetListResponseDto> getClosetList(Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        List<Closet> closetList = closetRepository.findByUser(user);
        List<ClosetListResponseDto> responseDtoList = new ArrayList<>();

        for (Closet closet: closetList) {
            ClosetListResponseDto responseDto = ClosetListResponseDto
                    .builder()
                    .closet(closet)
                    .build();
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }



}
