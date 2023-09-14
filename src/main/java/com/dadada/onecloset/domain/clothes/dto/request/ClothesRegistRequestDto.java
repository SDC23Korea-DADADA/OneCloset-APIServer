package com.dadada.onecloset.domain.clothes.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ClothesRegistRequestDto {

    private MultipartFile image;
    private String type;
    private Long color;
    private String material;
    private String description;
    private List<String> hashtagList;
    private List<String> weatherList;
    private List<String> tpoList;

}
