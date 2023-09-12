package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.clothes.dto.response.FastApiClothesAnalyzeResponseDto;
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

    public Boolean isClothes(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/clothes/check", HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
            return Boolean.parseBoolean(jsonElement.getAsJsonObject().get("isClothes").getAsString());
        }
        return false;
    }

    public FastApiClothesAnalyzeResponseDto getClothesInfoAndRemoveBackgroundImg(MultipartFile file) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = makeHttpEntity(file);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVER + "/clothes/rembg/info", HttpMethod.POST, requestEntity, String.class);
        JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        return FastApiClothesAnalyzeResponseDto.of(jsonElement);
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


}
