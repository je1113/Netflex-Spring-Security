전체 모듈 구조 (헥사고날 아키텍처 / Ports & Adapters)                                                                 
```
netflix (root)                                                                                                          ├── netflix-core/
│   ├── core-domain       → 도메인 모델 (의존성 없음)
│   ├── core-port         → 포트 인터페이스 (TmdbMoviePort 등)
│   ├── core-usecase      → 유스케이스 인터페이스 (FetchMovieUseCase 등)
│   └── core-service      → 유스케이스 구현체 (MovieService 등)
├── netflix-adapters/
│   ├── adapter-http      → 외부 HTTP 클라이언트 (TmdbMoviePort 구현)
│   ├── adapter-persistence → DB 어댑터
│   └── adapter-redis
├── netflix-apps/
│   ├── app-api           → Spring Boot 앱 (진입점)
│   └── app-batch
└── netflix-commons
```
