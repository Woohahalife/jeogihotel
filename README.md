# **💒 숙박 / 예약 서비스 만들기**


### **🖥️개발 기간**
2024-03-18 ~ 2024-04-05

### **👨‍💻멤버 소개 및 역할 분담**
| 이유빈 | 최건 |
| ------------ | ------------- |
| 전체 상품 목록 조회, 개별 상품 조회, 별점 생성| 회원가입, 로그인/로그아웃, 보안 처리|
| 상품 옵션 선택, 숙소와 객실의 생성, 수정, 삭제, 건님 말 잘 듣기| 예약, 예약 내역 조회, 페이징처리, 배포|

### **⚒️프로젝트 기술 스택**
- Backend: Java 17, Spring
- DataBase: Jpa, MySQL, H2Database
- Security: Spring Security
- Deploy: AWS, Docker
- Co-work: Github, Notion, Slack

### **📝ERD**
![Untitled](https://github.com/Woohahalife/KDT_BE7_Mini-Project/assets/110414025/f28f3821-807d-47de-b558-3fc488fe2904)

### **📄Api Docs ‐ 공통**

**view layer**로 최종적으로 보내지는 응답 양식에 대한 모델을 설명한다.

### 성공 응답 양식 예
```json
{
  "isSuccess": true,
  "statusCode": 200,
  "message": "요청에 성공했습니다.",
  "status": "SUCCESS",
  "data": "Test data"
}
```

성공 시 최종적으로 전달되는 응답 양식이다.<br/>
성공 여부는 true가 반환된다.

### 실패 응답 양식
```json
{
  "isSuccess": false,
  "statusCode": 500,
  "message": "INVALID_ERROR",
  "status": "예상치 못한 에러가 발생했습니다."
}
```

성공 시 최종적으로 전달되는 응답 양식이다.<br/>
성공 여부는 false가 반환된다.

### **📄Api Docs **
### 💡숙소

HTTP Method | URI | Descriptions
 -- | -- | -- 
POST | api/v1/accommodation/admin | 숙소 생성
DELETE | api/v1/accommodation/{accommodation_id}/delete | 숙소 삭제
POST | api//v1/accommodation/{accommodation_id}/update | 숙소 수정
GET | public-api/v1/accommodation | 숙소 전체 조회
GET | public-api/v1/accommodation/{accommodation_id}/detail | 숙소 단건 조회
POST | api/v1/discount | 할인율 생성
POST | api/v1/location | 지역 생성

### 💡객실
HTTP Method | URI | Descriptions
 -- | -- | -- 
POST | api/v1/accommodation/{accommodationId}/room | 객실 생성
DELETE | api/v1/accommodation/{accommodation_id}/room/{room_id}/delete | 객실 삭제
POST | api/v1/accommodation/{accommodation_id}/room/{room_id}/update | 객실 수정
GET | public-api/v1/accommodation/{accommodationId} | 숙소에 따른 객실 조회

### 💡예약
HTTP Method | URI | Descriptions
 -- | -- | -- 
POST | api/v1/reservation/insert | 예약 생성
GET | api/v1/reservation | 예약 조회

### 💡별점
HTTP Method | URI | Descriptions
 -- | -- | -- 
POST | api/v1/{accommodationId}/rate/create | 별점 생성

### 💡멤버
HTTP Method | URI | Descriptions
 -- | -- | -- 
POST | api/v1/member/token | 토큰 재발급
GET | api/v1/member/info/{memberId} | 멤버 정보 조회
POST | api//v1/member/authorization | 멤버 인증
POST | api/v1/member/update/{memberId} | 멤버 정보 갱신
POST | public-api/v1/member/join | 회원가입
POST | public-api/v1/member/login | 로그인


