package com.dadada.onecloset.domain.clothes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class FastAPIClothesAnalyzeResponseDto {

    private MultipartFile multipartFile;
    private String type;
    private String color;
    private String material;

}
