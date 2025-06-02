# FOODING-backend

## Backend 이용 기능
1. Server 구축 및 배포
2. Database 구축 및 관리
3. 회원가입 및 로그인 기능 (카카오 API 이용)
4. 이미지/영수증 인식 (YOLO + OCR) 관련 AI와 상호작용

## 폴더 구조
- `src/main/java/fooding/foodingback/Auth` : Kakao 로그인, 서버 배포 관련 코드
- `src/main/java/fooding/foodingback/User` : 회원 관련 코드
- `src/main/java/fooding/foodingback/Ingredient` : 식재료 관련 기능 코드
- `src/main/java/fooding/foodingback/global/config` : Kakao 로그인, 서버 배포 관련 코드
- `src/main/java/fooding/foodingback/global/util` : jwt 관련 코드
- `src/main/java/fooding/foodingback/FoodingBackApplication.java` : SpringBoot 프로젝트 Main 클래스
