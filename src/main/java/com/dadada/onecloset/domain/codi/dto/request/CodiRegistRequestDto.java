package com.dadada.onecloset.domain.codi.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CodiRegistRequestDto {

    private MultipartFile image;
    private String date;
    private List<Long> clothesList;

}
