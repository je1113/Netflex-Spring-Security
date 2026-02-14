의존관계
```
  app-api 의존:
    ├── core-usecase (compile)       → FetchMovieUseCase 인터페이스만 있음
    ├── adapter-http (runtimeOnly)   → TmdbMoviePort 구현체
    └── adapter-persistence (runtimeOnly)
```
작동 원리 (3단계)

1단계: 컴파일 타임
- app-api의 MovieController는 FetchMovieUseCase 인터페이스만 알면 컴파일 가능
- core-usecase 모듈에는 인터페이스만 있으므로 이것만 compile 의존으로 충분

2단계: 런타임 클래스패스 조립
- adapter-http, adapter-persistence 등이 runtimeOnly로 포함됨
- 이 어댑터 모듈들이 자신의 의존성(core-service 등)을 transitively 가져옴
- 결과적으로 MovieService(@Service), TmdbMovieListHttpClient(@Component) 등이 모두 런타임 클래스패스에 존재

3단계: Spring Boot 컴포넌트 스캔
@SpringBootApplication  // fast.campus.netflix 패키지 기준 스캔
public class NetflixApiAppication { ... }
- 모든 모듈이 fast.campus.netflix.* 패키지를 공유
- Spring Boot가 클래스패스의 모든 JAR에서 @Service, @Component 등을 자동 발견
- 인터페이스 ↔ 구현체 자동 매핑'
  빈 주입 체인
```mermaid

  MovieController (app-api)
  └─ needs FetchMovieUseCase (인터페이스, core-usecase)
      └─ Spring이 MovieService 발견 (@Service, core-service)
          └─ needs TmdbMoviePort (인터페이스, core-port)
              └─ Spring이 TmdbMovieListHttpClient 발견 (@Component, adapter-http)
                  └─ needs TmdbHttpClient → HttpClient → RestTemplate
```

