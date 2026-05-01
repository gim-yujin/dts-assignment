# 프로그램 직무 과제

Cursor IDE + Claude 환경에서 Spring Boot 기반 로그인/로그아웃 화면과 RDBMS 연동을 구현하기 위한 과제 프로젝트입니다.  
본 문서는 과제 범위, 기술 스택, 실행 방법, 제출 산출물을 정리합니다.

## 1) 과제 목표

- 로그인 화면 개발
- 로그아웃 화면 개발
- RDBMS 연동 및 데이터 저장/조회
- 데이터베이스 구조(ERD) 제출
- UI 프레임워크는 Bootstrap 사용

## 2) 기술 스택

- Language: Java 21.0.10
- Framework: Spring Boot 4.0.6, Spring Security 7.0.5, Spring Data JPA 4.0.5
- DB: PostgreSQL 16.13
- Build: Gradle 9.4.1
- Test: JUnit Jupiter 6.0.3
- Performance Test: k6 2.0.0
- View: Thymeleaf 3.1.5, Bootstrap 5.3.3

## 3) 프로젝트 실행 방법

### 3.1 PostgreSQL 준비

아래 예시는 로컬 실행 기준입니다.

```sql
CREATE DATABASE dts_assignment;
CREATE USER dts_user WITH PASSWORD 'dts_password';
GRANT ALL PRIVILEGES ON DATABASE dts_assignment TO dts_user;
```

### 3.2 환경 변수 설정 (권장)

```bash
export DB_URL=jdbc:postgresql://localhost:5432/dts_assignment
export DB_USERNAME=dts_user
export DB_PASSWORD=dts_password
```

### 3.3 애플리케이션 실행

```bash
./gradlew bootRun
```

기본 접속 주소:

- `http://localhost:8080/login` (로그인 화면)
- `http://localhost:8080/` (로그인 성공 화면)
- `http://localhost:8080/logout-success` (로그아웃 완료 화면)

## 4) 테스트

### 4.1 단위/통합 테스트 (JUnit)

```bash
./gradlew test
```

### 4.2 성능 테스트 (k6)

예시:

```bash
k6 run k6/login-load-test.js
```

## 5) 데이터베이스 설계 (ERD)

ERD 파일은 아래 경로 중 하나로 제출합니다.

- `docs/erd.png`
- `docs/erd.pdf`
- `docs/erd.drawio`

## 6) 필수 산출물 체크리스트

- ✅ 로그인 화면 시안 1개
- ✅ 로그아웃 화면 시안 1개
- ✅ 연동된 ERD 파일 1개

## 7) 참고 및 평가 포인트

- 인터뷰에서 과제 리뷰 및 기술 질의가 진행될 수 있습니다.
- 구현 완성도뿐 아니라 구조적 이해와 기본기를 중심으로 평가됩니다.
- 전형 결과 합격 시, 마감일로부터 3일 이내 안내 예정입니다.
