package com.dadada.onecloset.fastapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FastApiService {

    private final RestTemplate restTemplate;

    @Value("${AI_SERVER}")
    private String AI_SERVER;

    // 의류여부 판단
    public Boolean isClothes(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/clothes/check", HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
            return Boolean.parseBoolean(jsonElement.getAsJsonObject().get("isClothes").getAsString());
        }
        return false;
    }

    // 의류 정보와 배경 제거된 이미지를 받아온다
    public FastApiClothesAnalyzeResponseDto getClothesInfoAndRemoveBackgroundImg(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/clothes/rembg/info", HttpMethod.POST, requestEntity, String.class);
        JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
//        System.out.println(jsonElement);
        return FastApiClothesAnalyzeResponseDto.of(jsonElement);
    }

    // 배경제거한 이미지를 받아온다 => 의류 수정시 사용
    public String removeBackgroundImg(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/clothes/rembg", HttpMethod.POST, requestEntity, String.class);
        JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        return jsonElement.getAsJsonObject().get("url").getAsString();
    }

    // 가상피팅 모델 등록
    public FastApiModelRegistResponseDto registFittingModel(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/fitting/preprocess", HttpMethod.POST, requestEntity, String.class);
        JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        return FastApiModelRegistResponseDto.of(jsonElement);
    }

    // 가상피팅 진행



    public HttpEntity<MultiValueMap<String, Object>> makeHttpEntity(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        return new HttpEntity<>(body, headers);
    }


}
