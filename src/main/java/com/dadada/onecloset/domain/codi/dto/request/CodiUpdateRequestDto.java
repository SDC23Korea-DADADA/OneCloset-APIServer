package com.dadada.onecloset.domain.codi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CodiUpdateRequestDto {

    private MultipartFile image;
    private String info;

    @Getter
    @Setter
    @ToString
    public static class CodiInfoDto {

        private Long codiId;
        private String date;
        private List<Long> clothesList;

    }
}
