package fooding.foodingback.Auth.controller;

import fooding.foodingback.Auth.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        String jwtToken = kakaoLoginService.kakaoLogin(code);
        return ResponseEntity.ok(jwtToken);
    }
}
