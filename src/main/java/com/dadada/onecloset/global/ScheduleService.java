package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    @Value("${TUNING_SERVER}")
    private String TUNING_SERVER;

    private final ClothesRepository clothesRepository;
    private final WebClientUtil webClientUtil;

    @Transactional
    @Scheduled(fixedDelay = 1000L * 60L * 60 * 24L)
    public void fineTuningAsync() {
        List<Clothes> clothesList = clothesRepository.findByIsUseData(false);
        JSONObject jsonObject = getJsonObject(clothesList);
        log.info("fineTuningAsync");
        webClientUtil.post(TUNING_SERVER + "/additional/train", jsonObject, String.class)
                .subscribe(
                        response -> {
                            log.info("findTuning Response: {}", response);
                            for (Clothes clothes : clothesList) {
                                clothes.useClothesData();
                                clothesRepository.save(clothes);
                            }
                        },
                        error -> {
                            log.error(error.getMessage());
                            log.error("FINE TUNING ERR");
                        }
                );
    }


    private static JSONObject getJsonObject(List<Clothes> clothesList) {
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (Clothes clothes: clothesList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clothesId", clothes.getId().toString());
            jsonObject.put("url", clothes.getOriginImg());
            jsonObject.put("material", clothes.getMaterial().getMaterialName());
            jsonObject.put("type", clothes.getType().getTypeName());
            jsonObjectList.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("clothesUrl",jsonObjectList);
        return jsonObject;
    }

}
