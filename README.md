# GitHub User Explorer 🔍

An Android app to search GitHub users, view their profiles, and revisit recent searches — even offline. Built to practice and demonstrate production-grade Android architecture.

## Features

- 🔎 Search any GitHub user by username
- 👤 View profile — avatar, name, followers, following, public repos
- 📜 Recent searches list, cached locally and available offline
- ⚡ Offline-first — recent searches load instantly from local cache
- 🎨 Modern UI built entirely with Jetpack Compose + Material 3

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM + Clean Architecture (3-layer) |
| DI | Hilt |
| Networking | Retrofit + Gson |
| Local Storage | Room |
| Async | Kotlin Coroutines + Flow |
| Navigation | Jetpack Navigation Compose |
| Image Loading | Coil |
| Testing | JUnit + Mockito + Coroutines Test |

## Architecture

This project follows Clean Architecture with three distinct layers:

```
presentation/   → ViewModels, Composable screens, UI state
domain/         → UseCases, Repository interfaces, domain models (pure Kotlin, zero Android imports)
data/           → Repository implementations, Retrofit, Room, mappers
```

**Why this matters:** the domain layer has no framework dependencies, so business logic is fully unit-testable without an emulator. Dependencies only point inward — presentation and data both depend on domain, domain depends on nothing.

```
User taps Search
    → ViewModel
    → UseCase (validates input)
    → Repository interface
    → Repository implementation
    → Retrofit (network) + Room (cache)
    → Domain model returned
    → UI updates
```

## Key Implementation Details

- **Offline-first caching** — every successful search is cached in Room; recent searches always load from the local database, profile data refreshes from network when available
- **Dependency Injection with Hilt** — zero manual factories; `AppModule` provides Retrofit, Room, and bindings; ViewModels use `@HiltViewModel` and `hiltViewModel()` in Compose
- **Testable business logic** — `GetUserUseCase` and `ProfileViewModel` have unit tests using Mockito for mocking and `kotlinx-coroutines-test` for coroutine testing
- **Unidirectional data flow** — `MutableStateFlow` is private in ViewModels, exposed as read-only `StateFlow`; UI only reads state and dispatches events

## Screenshots

| Search Screen | Profile Screen |
|---|---|
| <img width="360" height="700" alt="Screenshot_20260626_112542" src="https://github.com/user-attachments/assets/8a92b94c-b75d-47a0-b89e-6c6379cff03f" /> |
| <img width="360" height="700" alt="Screenshot_20260626_112605" src="https://github.com/user-attachments/assets/630594a5-b0d0-4090-908c-a0bf0ab36f73" /> |

## Setup

1. Clone the repo
2. Open in Android Studio (Hedgehog or newer)
3. Sync Gradle
4. Run on an emulator or physical device — no API key required (uses public GitHub API)

## What I Learned Building This

This project started as a Retrofit + Room exercise and evolved over several iterations into a fully layered Clean Architecture app with dependency injection and unit tests. Each iteration taught a specific concept:

- v1 — Retrofit + Room with manual ViewModelFactory
- v2 — Migrated to Hilt, removed all manual factories
- v3 — Refactored to Clean Architecture with UseCases and Repository interfaces
- v4 — Added unit tests for ViewModel and UseCase layers

## License

MIT
