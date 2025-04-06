package fooding.foodingback.Ingredient.repository;

import fooding.foodingback.Ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//Ingredient를 DB에 저장하거나 불러오는 인터페이스
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 현재 등록된 재료 중 가장 큰 tag 값을 가져오는 쿼리 (재료 추가할때 tag+1 해야하므로)
    @Query("SELECT MAX(i.tag) FROM Ingredient i")
    Optional<Integer> findMaxTag();
}
