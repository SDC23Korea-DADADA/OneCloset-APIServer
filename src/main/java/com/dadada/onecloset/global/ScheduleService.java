package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.util.WebClientUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    @Value("${AI_SERVER}")
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
                            for (Clothes clothes : clothesList) {
                                clothes.useClothesData();
                            }
                        },
                        error -> {
                            log.error("FINE TUNING ERR");
                        }
                );
    }

//    private final RestTemplate restTemplate;


//    @Transactional
//    public void fineTuning() {
//        List<Clothes> clothesList = clothesRepository.findByIsUseData(false);
//
//        JSONObject jsonObject = getJsonObject(clothesList);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                TUNING_SERVER + "/fitting/finetuning",
//                HttpMethod.POST, request, String.class);
//
//        JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
//
//        if (jsonElement.getAsString().equals("true")) {
//            System.out.println("데이터 업데이트");
//        } else {
//            System.out.println("데이터 업데이트 안해");
//        }
//    }
//
    private static JSONObject getJsonObject(List<Clothes> clothesList) {
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (Clothes clothes: clothesList) {
            JSONObject jsonObject = new JSONObject();
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
