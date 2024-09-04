# 음식 주문 관리 플랫폼

> 2024.08.22 ~ 2024.09.04
> 

### 🍮 프로젝트 개요

`Java` `Spring Boot 3.X` `PostgreSQL` `Redis` `Docker` `Github` `Postman` 
- **목표**
    
    광화문 근처에서 운영될 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼 개발
    
- **요구사항**
    - 사용자 관리 기능
        - 로그인 및 회원가입 기능
        - 접근 권한 관리 : 가게, 손님, 관리자
    - 주문 및 결제 기능
        - 리뷰 및 평점 기능
    - 가게 및 메뉴 관리 기능
        - 운영 지역 및 음식점 분류
        - 상품 설명에 대한 AI API 연동
    - 데이터 보존 및 삭제 처리

---

### 🍮 팀원 및 역할

|  | **역할** |  | 관련 도메인 |
| --- | --- | --- | --- |
| **김종규** | 팀장 | 주문 및 결제 기능, 리뷰 및 평점 기능 | Order, OrderMenu |
| **박진우** | 팀원 | 로그인 및 회원가입 기능, 접근 권한 관리 | User |
| **오희정** | 팀원 | 가게 및 메뉴 관리 기능 | Store, Menu, Category, Region, AI  |

---

### 🍮 서비스 구성 및 실행방법
    

- **서비스 구성(인프라 설계도)**
![image](https://github.com/user-attachments/assets/d37683a2-c8fa-4490-ad60-dd051bd7fc94)

- **서비스 실행방법**
    
    ```bash
    1. application.yml 파일 내에 있는 secret-key 값을 수정한다.
        1.1 추가적으로 menu/service/generatMenuDescription 함수 내에서 Gemini로부터 발급 받은 키를 입력한다.
	2. application.yml 파일은 로컬 서버에 맞게 수정한다. 
	3. Docker-Compose 디렉토리에서 docker-compose up -d 를 입력한다.
	4. Spring Boot를 실행한다.
    ```
    
- **프로젝트 구조**
    
    `Monolithic Application` `Layered Architecture`  `RESTful API` `Entity & DTO`
    
    ```bash
    com.sparta.aiverification
    ├── AiVerificationApplication.java
    ├── Timestamped.java
    ├── common
    │   ├── CommonErrorCode.java
    │   ├── ErrorCode.java
    │   ├── ErrorResponse.java
    │   ├── GlobalExceptionHandler.java
    │   ├── RestApiException.java
    │   ├── RestApiResponse.java
    │   └── UserAuditorAware.java
    │ 
    ├── config
    │   ├── AppConfig.java
    │   ├── CacheConfig.java
    │   ├── QuerydslConfig.java
    │   └── WebSecurityConfig.java
    │ 
    ├── ai
    │   └── controller, dto, entity, repository, service
    ├── menu
    │   └── controller, dto, entity, repository, service
    │ 
    ├── order
    │   └── controller, dto, entity, repository, service
    ├── ordermenu
    │   └── controller, dto, entity, repository, service
    ├── payment
    │   └── controller, dto, entity, repository, service
    │ 
    ├── category
    │   └── controller, dto, entity, repository, service
    ├── region
    │   └── controller, dto, entity, repository, service
    ├── review
    │   └── controller, dto, entity, repository, service
    ├── store
    │   └── controller, dto, entity, repository, service
    │ 
    └── user
        ├── enums, jwt, security
        └── controller, dto, entity, repository, service
    ```
    

---

### API Docs 및 ERD
- **[API Docs](https://documenter.getpostman.com/view/37993476/2sAXjM5sA2)**
  
- **ERD**
> 모든 entity는 createdAt, createdBy, updatedAt, updatedBy, deletedAt, deletedBy가 존재한다.
>
![image](https://github.com/user-attachments/assets/e5db72f8-dd29-4363-b5a4-784a7082b2a5)

