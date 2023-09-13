package com.dadada.onecloset.domain.user.service;

import com.dadada.onecloset.domain.user.dto.request.CodeAndUriRequestDto;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialLoginService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${social-login.kakao.client}")
    private String KAKAO_CLIENT;

    @Value("${social-login.kakao.secret}")
    private String KAKAO_SECRET;

    @Value("${social-login.kakao.auth-uri}")
    private String KAKAO_AUTH_URI;

    @Value("${social-login.kakao.user-info-uri}")
    private String KAKAO_USER_INFO_URI;

    @Value("${social-login.naver.client}")
    private String NAVER_CLIENT;

    @Value("${social-login.naver.secret}")
    private String NAVER_SECRET;

    @Value("${social-login.naver.auth-uri}")
    private String NAVER_AUTH_URI;

    @Value("${social-login.naver.user-info-uri}")
    private String NAVER_USER_INFO_URI;

    @Value("${social-login.google.client}")
    private String GOOGLE_CLIENT;

    @Value("${social-login.google.secret}")
    private String GOOGLE_SECRET;

    @Value("${social-login.google.auth-uri}")
    private String GOOGLE_AUTH_URI;

    @Value("${social-login.google.user-info-uri}")
    private String GOOGLE_USER_INFO_URI;


    public DataResponse<HashMap<String, Object>> kakaoLogin(CodeAndUriRequestDto requestDto) throws IOException {
        String accessToken = getAccessTokenByKakao(requestDto);
        HashMap<String, Object> userInfo = getUserInfoByKakao(accessToken);
        HashMap<String, Object> jwt = getJWT(userInfo, LoginType.KAKAO);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public DataResponse<HashMap<String, Object>> naverLogin(CodeAndUriRequestDto requestDto) throws IOException {
        String accessToken = getAccessTokenByNaver(requestDto);
        HashMap<String, Object> userInfo = getUserInfoByNaver(accessToken);
        HashMap<String, Object> jwt = getJWT(userInfo, LoginType.NAVER);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public DataResponse<HashMap<String, Object>> googleLogin(CodeAndUriRequestDto requestDto) throws IOException {
        String accessToken = getAccessTokenByGoogle(requestDto);
        HashMap<String, Object> userInfo = getUserInfoByGoogle(accessToken);
        HashMap<String, Object> jwt = getJWT(userInfo, LoginType.GOOGLE);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public DataResponse<HashMap<String, Object>> kakaoLoginByAccessToken(String accessToken) throws IOException {
        HashMap<String, Object> jwt = getJWT(getUserInfoByKakao(accessToken), LoginType.KAKAO);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public DataResponse<HashMap<String, Object>> naverLoginByAccessToken(String accessToken) throws IOException {
        HashMap<String, Object> jwt = getJWT(getUserInfoByNaver(accessToken), LoginType.NAVER);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public DataResponse<HashMap<String, Object>> googleLoginByAccessToken(String accessToken) throws IOException {
        HashMap<String, Object> jwt = getJWT(getUserInfoByGoogle(accessToken), LoginType.GOOGLE);
        return new DataResponse<>(200, "로그인 성공", jwt);
    }

    public String getAccessTokenByKakao(CodeAndUriRequestDto requestDto) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> tokenRequest = getHttpEntityByKakao(requestDto);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(KAKAO_AUTH_URI, HttpMethod.POST, tokenRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    private HttpEntity<MultiValueMap<String, String>> getHttpEntityByKakao(CodeAndUriRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT);
        body.add("client_secret", KAKAO_SECRET);
        body.add("redirect_uri", requestDto.getRedirect());
        body.add("code", requestDto.getCode());

        return new HttpEntity<>(body, headers);
    }

    public String getAccessTokenByNaver(CodeAndUriRequestDto requestDto) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> tokenRequest = getHttpEntityByNaver(requestDto);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(NAVER_AUTH_URI, HttpMethod.POST, tokenRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    private HttpEntity<MultiValueMap<String, String>> getHttpEntityByNaver(CodeAndUriRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", NAVER_CLIENT);
        body.add("client_secret", NAVER_SECRET);
        body.add("redirect_uri", requestDto.getRedirect());
        body.add("code", requestDto.getCode());

        return new HttpEntity<>(body, headers);
    }

    public String getAccessTokenByGoogle(CodeAndUriRequestDto requestDto) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> tokenRequest = getHttpEntityByGoogle(requestDto);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(GOOGLE_AUTH_URI, HttpMethod.POST, tokenRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    private HttpEntity<MultiValueMap<String, String>> getHttpEntityByGoogle(CodeAndUriRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", GOOGLE_CLIENT);
        body.add("client_secret", GOOGLE_SECRET);
        body.add("redirect_uri", requestDto.getRedirect());
        body.add("code", requestDto.getCode());

        return new HttpEntity<>(body, headers);
    }

    public HashMap<String, Object> getUserInfoByKakao(String accessToken) throws IOException {

        HashMap<String, Object> userInfo = new HashMap<>();

        StringBuilder result = getStringBuilder(accessToken, KAKAO_USER_INFO_URI);
        JsonElement element = JsonParser.parseString(result.toString());

        String email = "";
        boolean has_email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
        if (has_email) {
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        }

        userInfo.put("loginId", element.getAsJsonObject().get("id").getAsString());
        userInfo.put("nickname", element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString());
        userInfo.put("profileImg", element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString());
        userInfo.put("email", email);

        return userInfo;
    }

    public HashMap<String, Object> getUserInfoByNaver(String accessToken) throws IOException {
        HashMap<String, Object> userInfo = new HashMap<>();

        StringBuilder result = getStringBuilder(accessToken, NAVER_USER_INFO_URI);
        JsonElement element = JsonParser.parseString(result.toString());

        userInfo.put("loginId", element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString());
        userInfo.put("nickname", element.getAsJsonObject().get("response").getAsJsonObject().get("nickname").getAsString());
        userInfo.put("profileImg", element.getAsJsonObject().get("response").getAsJsonObject().get("profile_image").getAsString());
        userInfo.put("email", element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString());
        return userInfo;
    }

    public HashMap<String, Object> getUserInfoByGoogle(String accessToken) throws IOException {
        HashMap<String, Object> userInfo = new HashMap<>();

        StringBuilder result = getStringBuilder(accessToken, GOOGLE_USER_INFO_URI);
        JsonElement element = JsonParser.parseString(result.toString());

        userInfo.put("loginId", element.getAsJsonObject().get("id").getAsString());
        userInfo.put("nickname", element.getAsJsonObject().get("name").getAsString());
        userInfo.put("profileImg", element.getAsJsonObject().get("picture").getAsString());
        userInfo.put("email", element.getAsJsonObject().get("email").getAsString());
        return userInfo;
    }

    private StringBuilder getStringBuilder(String accessToken, String reqUrl) throws IOException {
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        return result;
    }

    private HashMap<String, Object> getJWT(HashMap<String, Object> userInfo, LoginType loginType) {

        if (userService.isEmpty(userInfo.get("loginId").toString(), loginType)) {
            userService.enterUser(userInfo, loginType);
        }

        User user = userRepository.findByLoginIdAndLoginType(userInfo.get("loginId").toString(), loginType)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        HashMap<String, Object> result = new HashMap<>();

        String accessToken = JwtUtil.generateAccessToken(user.getId().toString());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId().toString());
        result.put("access-token", accessToken);
        result.put("refresh-token", refreshToken);

        return result;
    }
}