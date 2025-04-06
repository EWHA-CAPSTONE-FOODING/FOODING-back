package fooding.foodingback.Ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 프론트 -> 백엔드 (등록)
 * 프론트에서 식재료를 등록할 때 사용하는 요청 DTO
 * → name과 quantity만 받아도 됨 (tag는 백엔드가 자동 부여)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {
    private String name;     // 식재료 이름
    private int quantity;    // 수량
}
