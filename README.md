# 🎁 Manitto Frontend (KMP Web)

Kotlin Multiplatform + Compose HTML 기반 마니또 서비스 프론트엔드

## 🛠 기술 스택

- **Kotlin/JS** + Compose HTML
- **Ktor Client** - HTTP 요청
- **Kotlinx Serialization** - JSON 처리
- **Kotlinx Coroutines** - 비동기 처리

## 🚀 로컬 개발

```bash
# 개발 서버 실행
./gradlew jsBrowserDevelopmentRun --continuous

# 프로덕션 빌드
./gradlew jsBrowserDistribution
```

빌드 결과: `build/dist/js/productionExecutable/`

## 🌐 Vercel 배포

### 1. Vercel CLI로 배포

```bash
npm i -g vercel
vercel
```

### 2. 환경변수 설정 (Vercel Dashboard)

| 변수 | 설명 |
|------|------|
| `VITE_API_BASE_URL` | 백엔드 API URL (예: `https://api.manitto.com`) |
| `VITE_GOOGLE_CLIENT_ID` | Google OAuth Client ID |

### 3. GitHub 연동 (권장)

1. GitHub에 푸시
2. Vercel에서 Import
3. 환경변수 설정
4. 자동 배포!

## 📂 프로젝트 구조

```
src/jsMain/kotlin/party/manitto/
├── Main.kt              # 앱 진입점 + 라우팅
├── api/
│   ├── ApiClient.kt     # Ktor HTTP 클라이언트
│   └── Models.kt        # API 모델 (Serializable)
├── auth/
│   ├── AuthState.kt     # 인증 상태 관리
│   └── GoogleLogin.kt   # Google 로그인 버튼
└── ui/
    ├── Styles.kt        # CSS 스타일
    ├── LoginPage.kt     # 로그인 페이지
    ├── CreatePartyPage.kt
    ├── JoinPartyPage.kt
    ├── PartyStatusPage.kt
    └── MatchResultPage.kt
```

## 🔗 라우팅

| 경로 | 페이지 |
|------|--------|
| `#/` | 파티 생성 |
| `#/party/{id}/join` | 파티 참가 |
| `#/party/{id}/status` | 파티 상태 |
| `#/party/{id}/result` | 매칭 결과 |
