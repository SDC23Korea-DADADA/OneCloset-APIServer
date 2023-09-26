package com.dadada.onecloset.global;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class FineTuningDto {

    private String url;
    private String material;
    private String type;

}
