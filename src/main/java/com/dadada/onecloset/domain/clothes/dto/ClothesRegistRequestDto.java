package com.dadada.onecloset.domain.clothes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
@Setter
public class ClothesRegistRequestDto {

    private MultipartFile image;
    private String type;
    private String color;
    private String material;
    private String description;
    private List<String> hashtagList;
    private List<String> weatherList;
    private List<String> tpoList;

}
