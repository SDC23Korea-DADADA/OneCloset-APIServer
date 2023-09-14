package com.dadada.onecloset.domain.codi.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class CodiRegistRequestDto {

    private MultipartFile multipartFile;
    private String description;
    private List<Long> clothesList;

}
