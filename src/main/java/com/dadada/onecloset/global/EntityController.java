package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.clothes.entity.code.Material;
import com.dadada.onecloset.domain.clothes.entity.code.Type;
import com.dadada.onecloset.domain.clothes.repository.ColorRepository;
import com.dadada.onecloset.domain.clothes.repository.MaterialRepository;
import com.dadada.onecloset.domain.clothes.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/entity")
@RequiredArgsConstructor
public class EntityController {

    private final TypeRepository typeRepository;
    private final MaterialRepository materialRepository;
    private final ColorRepository colorRepository;


    @GetMapping("/type")
    public List<Type> getTypeList() {
        return typeRepository.findAll();
    }

    @GetMapping("/material")
    public List<Material> getMaterialList() {
        return materialRepository.findAll();
    }

    @GetMapping("/color")
    public List<Color> getColorList() {
        return colorRepository.findAll();
    }



}
