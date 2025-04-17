package fooding.foodingback.Ingredient.controller;

import fooding.foodingback.Ingredient.dto.IngredientRequestDto;
import fooding.foodingback.Ingredient.dto.IngredientResponseDto;
import fooding.foodingback.Ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 식재료 등록/조회 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients") // 공통 주소
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * [POST] /api/ingredients
     * 식재료 등록 API
     * 프론트에서 name, quantity만 보내주면,
     * tag는 자동으로 부여돼서 저장됨
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerIngredients(@RequestBody List<IngredientRequestDto> requestDtoList) {
        ingredientService.saveIngredients(requestDtoList);
    }

    /**
     * [GET] /api/ingredients
     * 등록된 식재료들을 tag 기준(FIFO)으로 정렬해서 조회하는 API
     */
    @GetMapping
    public List<IngredientResponseDto> getIngredients() {
        return ingredientService.getIngredients();
    }


    /**
     * YOLO 전용 API 생성
     */
    @PostMapping(value = "/yolo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerByYolo(@RequestPart("image") MultipartFile image) {
        ingredientService.registerIngredientsByYolo(image);
    }


    /**
     * [POST] /api/ingredients/ocr
     * OCR 이미지 업로드 API
     * → 사용자가 영수증 이미지 업로드 -> 백엔드가 OCR 서버에 전송, 분석 결과 저장
     */
    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerByOcr(@RequestPart("image") MultipartFile image) {
        ingredientService.registerIngredientsByOcr(image);
    }
}
