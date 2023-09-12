package com.dadada.onecloset.domain.clothes.dto.response;

import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.laundrysolution.entity.CareTip;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import com.dadada.onecloset.domain.laundrysolution.entity.LaundryTip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesAnalyzeResponseDto {

    private String image;

    private String color;
    private String colorCode;

    private String type;
    private String material;

    private String laundry;
    private String dryer;
    private String airDresser;

    private List<String> laundryTip;
    private List<String> careTip;

    public static ClothesAnalyzeResponseDto of(FastApiClothesAnalyzeResponseDto responseDto, Color color, ClothesCare clothesCare) throws IOException {

        List<String> laundryTip = new ArrayList<>();
        for (LaundryTip laundry : clothesCare.getLaundryTipList()) {
            laundryTip.add(laundry.getTip());
        }

        List<String> careTip = new ArrayList<>();
        for (CareTip care : clothesCare.getCareTipList()) {
            careTip.add(care.getTip());
        }

        return ClothesAnalyzeResponseDto
                .builder()
                .image(responseDto.getUrl())
                .color(color.getColorName())
                .colorCode(color.getCode())
                .type(responseDto.getType())
                .material(responseDto.getMaterial())
                .laundry(clothesCare.getLaundryCourse())
                .dryer(clothesCare.getDryerCourse())
                .airDresser(clothesCare.getAirDresserCourse())
                .laundryTip(laundryTip)
                .careTip(careTip)
                .build();
    }

}
