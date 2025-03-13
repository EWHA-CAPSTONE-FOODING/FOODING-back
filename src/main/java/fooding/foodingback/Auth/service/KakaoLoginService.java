package fooding.foodingback.Auth.service;

import fooding.foodingback.Auth.config.JwtTokenProvider;
import fooding.foodingback.Auth.dto.KakaoUserInfo;
import fooding.foodingback.User.domain.User;
import fooding.foodingback.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String kakaoLogin(String code) {
        // 1️⃣ 카카오에서 Access Token 받기
        String accessToken = getKakaoAccessToken(code);

        // 2️⃣ Access Token으로 사용자 정보 가져오기
        KakaoUserInfo userInfo = getKakaoUserInfo(accessToken);

        // 3️⃣ 기존 사용자 확인 & 신규 회원 가입
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = userRepository.save(new User(userInfo));
        }

        // 4️⃣ JWT 토큰 발급
        return jwtTokenProvider.createToken(user.getEmail());
    }

    private String getKakaoAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "카카오 REST API 키 입력");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Map.class);

        Map<String, Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");

        return new KakaoUserInfo(
                response.getBody().get("id").toString(),
                kakaoAccount.get("email").toString(),
                ((Map<String, String>) kakaoAccount.get("profile")).get("nickname")
        );
    }
}
