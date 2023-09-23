package com.dadada.onecloset.domain.codi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class CodiRegistRequestDto {

    private MultipartFile image;
    private String info;

    @Getter
    @Setter
    public static class CodiInfoDto {
        private String date;
        private List<Long> clothesList;
    }
}
