package fooding.foodingback.Auth.controller;

import fooding.foodingback.Auth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/kakao/callback")
    public RedirectView kakaoLogin(@RequestParam String code) {
        // 카카오 로그인 로직 실행 (JWT는 생성하지만 사용 안 함)
        kakaoAuthService.kakaoLogin(code);

        // 프론트엔드로 리디렉션
        return new RedirectView("https://sparkling-naiad-b7cadc.netlify.app/");
    }
}
