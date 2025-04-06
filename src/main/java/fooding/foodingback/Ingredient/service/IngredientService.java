package fooding.foodingback.Ingredient.service;

import fooding.foodingback.Ingredient.domain.Ingredient;
import fooding.foodingback.Ingredient.dto.IngredientRequestDto;
import fooding.foodingback.Ingredient.dto.IngredientResponseDto;
import fooding.foodingback.Ingredient.repository.IngredientRepository;
import fooding.foodingback.global.util.MultipartInputStreamFileResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 식재료 저장 및 조회를 담당하는 서비스
 */
@Service
@RequiredArgsConstructor // final 붙은 필드를 자동 생성자 주입해줌
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    /**
     * 식재료를 저장하는 메서드
     * - 기존 tag 중 가장 큰 값을 찾아서, 그 다음 tag부터 부여
     */
    public void saveIngredients(List<IngredientRequestDto> dtoList) {
        int currentMaxTag = ingredientRepository.findMaxTag().orElse(0); // 없으면 0

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < dtoList.size(); i++) {
            IngredientRequestDto dto = dtoList.get(i);
            int tag = currentMaxTag + i + 1; // 순서대로 tag 증가
            ingredients.add(new Ingredient(dto.getName(), dto.getQuantity(), tag));
        }

        ingredientRepository.saveAll(ingredients); // 한 번에 저장
    }

    /**
     * 저장된 식재료들을 tag 오름차순(FIFO)으로 조회하는 메서드
     */
    public List<IngredientResponseDto> getIngredients() {
        return ingredientRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Ingredient::getTag)) // tag 기준 정렬
                .map(i -> new IngredientResponseDto(i.getName(), i.getQuantity(), i.getTag()))
                .collect(Collectors.toList());
    }

    /**
     * YOLO 결과 받아서 저장하는 service 메서드
     */
    public void registerIngredientsByYolo(MultipartFile image) {
        List<IngredientRequestDto> yoloResults = callYoloServer(image); // 분석 결과 가져오기
        saveIngredients(yoloResults); // 기존 저장 로직 재사용
    }

    /**
     * YOLO 서버 호출 메서드
     */
    private List<IngredientRequestDto> callYoloServer(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new MultipartInputStreamFileResource(image.getInputStream(), image.getOriginalFilename()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<IngredientRequestDto[]> response = restTemplate.postForEntity(
                    "http://YOLO_SERVER_IP:5000/yolo", // YOLO 서버 주소로 바꿔줘!
                    requestEntity,
                    IngredientRequestDto[].class
            );

            return Arrays.asList(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // 실패 시 빈 리스트
        }
    }


}
