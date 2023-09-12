package com.dadada.onecloset.domain.fitting.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FittingSaveRequestDto {

    private MultipartFile image;
    private Long modelId;
    private List<Long> clothesIdList;

}
