package com.dadada.onecloset.domain.user.service;

import com.dadada.onecloset.domain.user.dto.KakaoCodeRequestDto;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {

    private final UserRepository userRepository;

    @Value("${KAKAO_CLIENT}")
    private String CLIENT;

    @Value("${KAKAO_SECRET}")
    private String SECRET;

    public ResponseEntity<?> kakaoLogin(KakaoCodeRequestDto requestDto){
        String access_token = getAccessToken(requestDto);
        HashMap<String, Object> userInfo = getUserInfo(access_token);
        HashMap<String, Object> JWT = getJWT(userInfo);
        return new ResponseEntity<>(JWT, HttpStatus.OK);
    }

    public HashMap<String, Object> getJWT(HashMap<String, Object> userInfo) {
        if (isEmpty(userInfo.get("loginId").toString())) {
            enterUser(userInfo);
        }

        // 유저 정보 조회후 JWT
        User user = userRepository.findByLoginId(userInfo.get("loginId").toString())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        // JWT

        return userInfo;
    }

    public void enterUser(HashMap<String, Object> userInfo) {
        User user = User
                .builder()
                .loginId(userInfo.get("loginId").toString())
                .loginType(LoginType.KAKAO)
                .nickname(userInfo.get("nickname").toString())
                .profileImg(userInfo.get("profileImg").toString())
                .build();
        userRepository.save(user);
    }

    // 존재하는 유저 인지 검사
    private boolean isEmpty(String loginId) {
        Optional<User> checkUser = userRepository.findByLoginId(loginId);
        return checkUser.isEmpty();
    }


    public String getAccessToken(KakaoCodeRequestDto requestDto) {

        String code = requestDto.getCode();
        String redirect_uri = requestDto.getRedirect();

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT);
        body.add("client_secret", SECRET);
        body.add("redirect_uri", redirect_uri);
        body.add("code", code);

        // HTTP Request
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // getAccessToken
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode.get("access_token").asText();
    }

    public HashMap<String, Object> getUserInfo(String access_token) {

        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            // read response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while((line = br.readLine())!=null) {
                result.append(line);
            }

            JsonElement element = JsonParser.parseString(result.toString());
            String loginId = element.getAsJsonObject().get("id").getAsString();
            String profileImg = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            userInfo.put("loginId", loginId);
            userInfo.put("nickname", nickname);
            userInfo.put("profileImg", profileImg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

}