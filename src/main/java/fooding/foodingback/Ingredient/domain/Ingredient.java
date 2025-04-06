package fooding.foodingback.Ingredient.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//식재료 하나를 DB에 저장할 때 사용되는 클래스
@Entity // 이 클래스는 DB 테이블과 연결됨을 의미
@Getter // 외부에서 값을 읽을 수 있도록 Getter 생성
@NoArgsConstructor // 기본 생성자 생성
public class Ingredient {

    @Id // 기본 키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    private String name;       // 식재료 이름
    private int quantity;      // 수량
    private int tag;           // 등록 순서에 따른 우선순위 (작을수록 먼저 등록된 것)

    // 실제로 사용할 생성자
    public Ingredient(String name, int quantity, int tag) {
        this.name = name;
        this.quantity = quantity;
        this.tag = tag;
    }
}
