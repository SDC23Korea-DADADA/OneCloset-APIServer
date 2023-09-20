package com.dadada.onecloset.fastapi;

import com.dadada.onecloset.domain.fitting.dto.FittingCheckDataDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.global.DataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public FittingResultResponseDto fitting(List<FastApiFittingRequestDto> fittingCheckDataDtoList) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = getHttpEntity(headers, fittingCheckDataDtoList);

        RestTemplate rt = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String personResultAsJsonStr = rt.postForObject("http://127.0.0.1:8000/fast/fitting/", request, String.class);
        JsonNode jsonNode = objectMapper.readTree(personResultAsJsonStr);

        // Fast API에서 출력시키기
        FittingResultResponseDto responseDto = new FittingResultResponseDto();
        responseDto.setOriginImg("https://fitsta-bucket.s3.ap-northeast-2.amazonaws.com/6ceee621-1dd4-4d4a-aec6-31a7b204d98f-images.jpg");
        responseDto.setFittingImg(jsonNode.path("image").asText());
        return responseDto;

    }


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


    private static HttpEntity<String> getHttpEntity(HttpHeaders headers, List<FastApiFittingRequestDto> fittingCheckDataDtoList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "model url");
        jsonObject.put("labelMap", "seg url");
        jsonObject.put("skeleton", "pose url");
        jsonObject.put("keypoint", "dense url");
        jsonObject.put("dense", "points");
        jsonObject.put("denseNpz", "points");
        jsonObject.put("clothesList", fittingCheckDataDtoList);
        return new HttpEntity<>(jsonObject.toString(), headers);

    }

}
