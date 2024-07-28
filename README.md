# **💒 숙박 / 예약 서비스 구현 프로젝트**


### **🖥️개발 기간**
2024-03-18 ~ 2024-04-05

### **👨‍💻멤버 소개 및 역할 분담**
| 이유빈 | 최건 |
| ------------ | ------------- |
| 숙소 조회, 객실 조회, 별점 생성| 회원가입, 로그인/로그아웃, 보안 처리|
| 상품 옵션 선택, 객실과 숙소의 생성, 수정, 삭제| 예약, 예약 내역 조회, 페이징처리, 배포|

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

### 📄Api Docs
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
GET | public-api/v1/member/logout | 로그 아웃

### ‼️ 이슈
- 예약 로직
- 이미지 관리 방식
- 배포 관련 이슈
- 보안 토큰 처리 이슈

### 리팩토링 진행 과정
- 무중단 배포 전환   
 ![image](https://github.com/Woohahalife/KDT_BE7_Mini-Project/assets/140988037/06cd30ac-ca06-4fa4-89e6-cf5472205596)   
 update된 프로젝트의 배포~실행까지의 과정에서 생기는 중단점 제거를 위해   
 BLUE/GREEN 방식의 무중단 배포 도입 및 Rollback 시나리오에 대한 대응책 구축

- S3활용 imageUploader 도입   
  이미지 파일을 프로젝트 외부에서 관리하고, 자유롭게 view 페이지로 전달할 수 있도록   
  S3 storage를 활용해 imageUploader를 도입 및 관리 기능 구현

- 예약을 하고 싶은 객실을 저장할 수 있는 basket 구현 및 공지사항 board 도입    
  선택한 숙소를 바로 예약하기 전 소비자에게 선택할 시간을 부여할 수 있도록
  대상 내역을 저장할 수 있고 여러 내역을 동시에 예약할 수 있는 basket 구현
    
  관리자와 소비자가 소통할 수 있는 공지사항 board 도입


