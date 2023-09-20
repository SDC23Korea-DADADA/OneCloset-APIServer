package com.dadada.onecloset.domain.fitting.dto;

import com.dadada.onecloset.fastapi.FastApiFittingRequestDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class FittingCheckDataDto {

    private Boolean check;
    private List<FastApiFittingRequestDto> fittingRequestDtoList;

    @Builder
    FittingCheckDataDto(Boolean check, List<FastApiFittingRequestDto> fittingRequestDtoList) {
        this.check = check;
        this.fittingRequestDtoList = fittingRequestDtoList;
    }

}
