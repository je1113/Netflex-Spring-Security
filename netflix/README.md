# Netflix Clone - 헥사고날 아키텍처 실습 프로젝트

> 출처: [kdohyeon/fcss-project](https://github.com/kdohyeon/fcss-project/tree/main)

Spring Boot + React 기반의 넷플릭스 클론 프로젝트입니다.
**헥사고날 아키텍처(Ports & Adapters)**를 적용한 멀티 모듈 구조로 구성되어 있습니다.

---

## 기술 스택

| 영역 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.3.3 |
| Build | Gradle (멀티 모듈) |
| Database | MySQL 8.0, Spring Data JPA, Flyway |
| Cache | Redis |
| Query | QueryDSL 5.0.0 |
| Auth | Spring Security, JWT (jjwt 0.12.6), OAuth2 (Kakao) |
| Frontend | React 18, React Router 6, Material-UI 6, Axios |
| Infra | Docker Compose |

---

## 프로젝트 구조

```
netflix/
├── netflix-core/                  # 핵심 비즈니스 로직 (외부 의존성 없음)
│   ├── core-domain/               # 도메인 모델 (NetflixUser, NetflixMovie 등)
│   ├── core-port/                 # 포트 인터페이스 (TmdbMoviePort, SearchUserPort 등)
│   ├── core-usecase/              # 유스케이스 인터페이스 (FetchMovieUseCase 등)
│   └── core-service/              # 유스케이스 구현체 (MovieService, UserService 등)
├── netflix-adapters/              # 외부 시스템 어댑터
│   ├── adapter-http/              # 외부 API 클라이언트 (TMDB, Kakao OAuth)
│   ├── adapter-persistence/       # DB 어댑터 (JPA 엔티티, 레포지토리)
│   └── adapter-redis/             # Redis 캐시 어댑터
├── netflix-apps/                  # 애플리케이션 진입점
│   ├── app-api/                   # Spring Boot REST API
│   └── app-batch/                 # 배치 처리
├── netflix-commons/               # 공통 유틸리티, 예외 처리
├── netflix-frontend/              # React 프론트엔드
└── infra/                         # Docker Compose (MySQL, Redis)
```

---

## 아키텍처

헥사고날 아키텍처로 내부 비즈니스 로직이 외부 기술에 의존하지 않도록 설계되었습니다.

```
[Controller (app-api)]
        ↓
[UseCase Interface (core-usecase)]
        ↓
[Service Implementation (core-service)]
        ↓
[Port Interface (core-port)]
        ↙        ↓        ↘
[adapter-http] [adapter-persistence] [adapter-redis]
        ↓              ↓                   ↓
  TMDB / Kakao     MySQL DB            Redis
```

- `core-*` 모듈은 서로에게만 의존하며 어댑터를 알지 못합니다.
- `adapter-*` 모듈이 포트 인터페이스를 구현하여 런타임에 주입됩니다.

---

## 주요 기능

- **회원 관리**: 이메일/비밀번호 회원가입, 카카오 소셜 로그인
- **인증/인가**: JWT Access/Refresh 토큰, Spring Security, 역할 기반 접근 제어
- **영화 조회**: TMDB API 연동, 페이징/장르 필터, 영화 좋아요
- **구독 관리**: 구독 등록/변경/해지 (FREE, BRONZE, SILVER, GOLD)
- **감사 로그**: 사용자 요청 이력 자동 기록 (IP, URL, 헤더 등)

---

## 실행 방법

### 1. 인프라 실행 (Docker)

```bash
cd infra
docker-compose up -d
```

MySQL(3306), Redis(6379)가 실행됩니다.

### 2. 백엔드 실행

```bash
./gradlew :netflix-apps:app-api:bootRun
```

기본 포트: `http://localhost:8080`

### 3. 프론트엔드 실행

```bash
cd netflix-frontend
npm install
npm start
```

기본 포트: `http://localhost:3000`

---

## 주요 API 엔드포인트

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/api/v1/user/register` | 회원가입 |
| POST | `/api/v1/auth/login` | 로그인 |
| POST | `/api/v1/auth/reissue` | 토큰 재발급 |
| GET | `/api/v1/auth/callback` | 카카오 OAuth 콜백 |
| POST | `/api/v1/movie/search` | 영화 목록 조회 |
| GET | `/api/v1/user/{email}` | 사용자 정보 조회 |

---

## 데이터베이스 스키마

Flyway를 통해 자동 마이그레이션됩니다 (`V1__init.sql`).

| 테이블 | 설명 |
|--------|------|
| `users` | 일반 사용자 계정 |
| `social_users` | 소셜 로그인 사용자 |
| `tokens` | JWT 토큰 저장 |
| `movies` | 영화 정보 |
| `user_movie_likes` | 사용자 영화 좋아요 |
| `user_subscriptions` | 구독 정보 |
| `user_histories` | 감사 로그 |

---

## 사전 요구사항

- Java 17+
- Node.js 18+
- Docker & Docker Compose
- TMDB API Key ([https://www.themoviedb.org](https://www.themoviedb.org))
- Kakao OAuth App 등록 ([https://developers.kakao.com](https://developers.kakao.com))
