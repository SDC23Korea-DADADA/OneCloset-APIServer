package com.dadada.onecloset.domain.clothes.dto;

import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.laundrysolution.entity.CareTip;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import com.dadada.onecloset.domain.laundrysolution.entity.LaundryTip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClothesAnalyzeResponseDto {

    private byte[] image;

    private String color;
    private String colorCode;

    private String type;
    private String material;

    private String laundry;
    private String dryer;
    private String airDresser;

    private List<String> laundryTip;
    private List<String> careTip;

    @Builder
    public ClothesAnalyzeResponseDto(FastAPIClothesAnalyzeResponseDto responseDto, Color color, ClothesCare clothesCare, List<String> laundryTip, List<String> careTip) throws IOException {
        this.image = responseDto.getMultipartFile().getBytes();
        this.color = color.getColorName();
        this.colorCode = color.getCode();
        this.type = responseDto.getType();
        this.material = responseDto.getMaterial();
        this.laundry = clothesCare.getLaundryCourse();
        this.dryer = clothesCare.getDryerCourse();
        this.airDresser = clothesCare.getAirDresserCourse();
        this.laundryTip = laundryTip;
        this.careTip = careTip;
    }

}
