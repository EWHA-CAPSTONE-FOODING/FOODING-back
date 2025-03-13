package fooding.foodingback.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {
    private String id;
    private String email;
    private String nickname;
}
