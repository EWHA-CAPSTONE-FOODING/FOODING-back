package fooding.foodingback.Ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 백엔드 -> 프론트 (조회)
 * 프론트에서 식재료를 조회할 때 응답용 DTO
 * → name, quantity, tag를 모두 보여줌
 */
@Data
@AllArgsConstructor
public class IngredientResponseDto {
    private String name;     // 식재료 이름
    private int quantity;    // 수량
    private int tag;         // 등록 순서 (작을수록 먼저 등록됨)
}
