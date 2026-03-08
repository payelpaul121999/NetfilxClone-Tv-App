# 🎬 Netflix TV Clone — Android TV App

A production-grade **Netflix clone** built for **Android TV** using **Jetpack Compose**. Features D-pad navigation, hero banners, content rows, video playback, profile selection, search, and a full detail screen — all styled to match Netflix's real UI.

<br/>




## ✨ Features

- 🎭 **Profile Selection** — "Who's watching?" screen with avatar cards, Kids profile badge, Add Profile button, and Manage Profiles
- 🎬 **Hero Banner** — Full-width backdrop with gradient, `#1 IN INDIA TODAY` badge, Play / More Info / My List buttons
- 📋 **Content Rows** — Horizontal lazy rows: Continue Watching, Top 10, New on Netflix, Trending, Genre rows
- 🃏 **Focusable Cards** — D-pad focus with `1.08×` scale + white border, `TOP 10` and `NEW` badges
- 📄 **Movie Detail Screen** — Backdrop, cast, genres, rating, action buttons, More Like This row
- ▶️ **Video Player** — ExoPlayer with custom controls overlay, progress scrubber, ±10s seek, mute, auto-hide controls
- 🔍 **Search Screen** — Real-time search + 12-category browsable grid
- 📌 **My List** — Add/remove toggle, persisted in ViewModel, remove button on focus
- 🗂️ **Side Navigation** — Animated expandable sidebar, red left indicator, icons-only when collapsed
- ⚡ **Loading States** — Shimmer placeholders + spinner
- 🎨 **Animations** — Scale on focus, fade transitions between screens, animated nav expand/collapse

<br/>

## 🏗️ Project Structure

```
app/src/main/java/com/netflix/tvclone/
│
├── data/
│   ├── model/
│   │   └── Models.kt                  # Movie, ContentRow, UserProfile, NavigationItem
│   └── repository/
│       └── NetflixRepository.kt       # Mock data + Flow-based data access
│
├── ui/
│   ├── components/
│   │   ├── Components.kt              # FocusableMovieCard, NetflixButton, ShimmerCard,
│   │   │                              #   GenreChip, MaturityBadge, ProfileAvatar
│   │   ├── HeroBanner.kt              # Full-width hero with gradient + action buttons
│   │   └── NavComponents.kt           # NetflixSideNav, TopNavBar, NavItem, navItems
│   │
│   ├── screens/
│   │   ├── HomeScreen.kt              # Hero + content rows
│   │   ├── MovieDetailScreen.kt       # Full detail — backdrop, cast, similar titles
│   │   ├── VideoPlayerScreen.kt       # ExoPlayer + custom controls overlay
│   │   ├── SearchScreen.kt            # Live search + category grid
│   │   ├── MyListScreen.kt            # Saved titles grid
│   │   └── ProfileSelectionScreen.kt  # Who's watching?
│   │
│   └── theme/
│       └── Theme.kt                   # NetflixColors, NetflixDimensions, NetflixTheme
│
├── navigation/
│   └── Navigation.kt                  # NavHost, Screen routes, MainScreen scaffold
│
├── viewmodel/
│   └── HomeViewModel.kt               # HomeUiState, SearchUiState, StateFlow
│
├── AppModule.kt                       # Koin DI — Repository + ViewModel bindings
├── NetflixApp.kt                      # Application class — Koin startup
└── MainActivity.kt                    # Entry point — fullscreen TV Activity
```

<br/>

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Kotlin** | 1.9.0 | Primary language |
| **Jetpack Compose** | BOM 2024.02.00 | Declarative UI |
| **Compose Material3** | via BOM | Components & theming |
| **Compose Navigation** | 2.7.7 | Screen routing |
| **Koin** | 3.5.0 | Dependency injection |
| **ExoPlayer (Media3)** | 1.3.0 | Video playback |
| **Coil** | 2.4.0 | Async image loading |
| **Kotlinx Serialization** | 1.6.0 | JSON model parsing |
| **Lottie Compose** | 4.0.0 | Lottie animations |
| **Accompanist Pager** | 0.22.0 | ViewPager support |
| **Core Splashscreen** | 1.0.0 | Launch screen |
| **Paging3 Compose** | 3.2.1 | Paginated lists |
| **Leanback** | 1.0.0 | Android TV launcher |
| **Firebase Messaging** | 23.4.1 | Push notifications |
| **Razorpay** | 1.6.20 | In-app payments |

<br/>

## 🏛️ Architecture

```
UI Layer (Compose Screens)
        ↕
ViewModel Layer (StateFlow + Koin)
        ↕
Repository Layer (Flow-based data)
        ↕
Data Layer (Mock / API / Room)
```

- **Pattern**: MVVM (Model-View-ViewModel)
- **State management**: `StateFlow` + `collectAsState()`
- **DI**: Koin — `single {}` for repository, `viewModel {}` for ViewModel
- **Navigation**: Compose Navigation with typed `Screen` sealed class routes

<br/>

## 🎨 Design System

### Colors
```kotlin
NetflixColors.Red          = #E50914   // Brand red — buttons, badges, logo
NetflixColors.Black        = #000000   // Page background
NetflixColors.DarkGray     = #141414   // Card/surface background
NetflixColors.TextPrimary  = #FFFFFF   // Main text
NetflixColors.TextSecondary= #B3B3B3   // Subtitles, metadata
NetflixColors.Gold         = #FFD700   // Star ratings
NetflixColors.FocusedBorder= #FFFFFF   // D-pad focus ring
```

### TV-optimised Dimensions
```kotlin
NetflixDimensions.cardWidth        = 200.dp   // Landscape card
NetflixDimensions.cardHeight       = 120.dp
NetflixDimensions.heroHeight       = 500.dp   // Hero banner
NetflixDimensions.focusBorderWidth = 3.dp     // D-pad focus ring
NetflixDimensions.cornerRadius     = 4.dp
NetflixDimensions.paddingXxl       = 48.dp    // Screen edge padding
```

<br/>

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android TV Emulator or physical TV device

### Setup

**1. Clone the repo**
```bash
git clone https://github.com/yourusername/NetflixTVClone.git
cd NetflixTVClone
```

**2. Add `google-services.json`**

Download from Firebase Console and place at:
```
app/google-services.json
```
> Or remove Firebase from `build.gradle.kts` if not needed.

**3. Add TV launcher banner**

Create a `320×180px` PNG at:
```
app/src/main/res/drawable/app_banner.png
```

**4. Build & run**
```bash
./gradlew assembleDebug
```

Deploy to an Android TV emulator via Android Studio or ADB:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

<br/>

## 🎮 TV Remote Navigation

| Remote Key | Action |
|---|---|
| **D-Pad** Up/Down/Left/Right | Navigate between cards |
| **Select / OK** | Click focused item |
| **Back** | Navigate back / collapse nav |
| **D-Pad Left** (from content) | Expand side navigation |
| **Menu** | Toggle side nav |

<br/>

## 🔌 Connecting Real Data

Replace `NetflixRepository` mock data with any API (e.g. TMDB):

```kotlin
// Add Retrofit
class NetflixRepository(private val api: TMDBApiService) {

    fun getContentRows(): Flow<List<ContentRow>> = flow {
        val popular  = api.getPopular()
        val trending = api.getTrending()
        emit(listOf(
            ContentRow(1, "Popular Now", popular.results.map { it.toMovie() }),
            ContentRow(2, "Trending",    trending.results.map { it.toMovie() })
        ))
    }
}

// Update AppModule.kt
val appModule = module {
    single { RetrofitClient.create() }          // add Retrofit singleton
    single { NetflixRepository(get()) }         // inject api
    viewModel { HomeViewModel(get()) }
}
```

<br/>

## 📦 Key Dependencies — `build.gradle.kts`

```kotlin
// Compose BOM — controls all Compose versions
implementation(platform("androidx.compose:compose-bom:2024.02.00"))

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.7")

// Koin DI
implementation(platform("io.insert-koin:koin-bom:3.5.0"))
implementation("io.insert-koin:koin-android:3.5.0")
implementation("io.insert-koin:koin-androidx-compose:3.4.1")

// ExoPlayer
implementation("androidx.media3:media3-exoplayer:1.3.0")
implementation("androidx.media3:media3-ui:1.3.0")

// Image loading
implementation("io.coil-kt:coil-compose:2.4.0")

// TV Leanback
implementation("androidx.leanback:leanback:1.0.0")
```

<br/>

## 📋 Screens Overview

| Screen | File | Route |
|---|---|---|
| Profile Selection | `ProfileSelectionScreen.kt` | `profile_selection` |
| Home | `HomeScreen.kt` | `main → home` |
| TV Shows | `HomeScreen.kt` (reused) | `main → tv_shows` |
| Movies | `HomeScreen.kt` (reused) | `main → movies` |
| My List | `MyListScreen.kt` | `main → my_list` |
| Search | `SearchScreen.kt` | `main → search` |
| Movie Detail | `MovieDetailScreen.kt` | `detail/{movieId}` |
| Video Player | `VideoPlayerScreen.kt` | `player/{movieId}` |

<br/>

## 📄 License

```
MIT License — free to use, modify, and distribute.
```

<br/>
## 📸 Screenshots

| Profile Selection | Home Screen |
|:-:|:-:|
| ![Profile Selection]<img width="820" height="400" alt="profile" width="30%" src="https://github.com/user-attachments/assets/6c2924b1-e6f2-4fa0-8c8c-a16c7aa6f0a2" />
 | ![Home Screen]<img width="820" height="400" alt="home_screen" width="30%" src="https://github.com/user-attachments/assets/39e4d200-89b6-4c86-80b3-a7ed7fcbf099" />
|

| Content Rows | Detail Screen |
|:-:|:-:|
| ![Content Rows]<img width="820" height="400" alt="home_screen_2" width="30%" src="https://github.com/user-attachments/assets/a11c7970-6007-4da3-8981-61456955bcdd" />
 | ![Detail Screen]<img width="820" height="400" alt="details_screen" width="30%" src="https://github.com/user-attachments/assets/56abd99d-f9fe-47a5-8379-af23c1e8f383" />
 | VIDEO |
<video src ="https://github.com/user-attachments/assets/0663d36a-e1ba-4dcf-a81d-58663caffbb4" width="30%" />
## 🙏 Acknowledgements

- UI inspired by the Netflix Android TV app
- Sample images via [picsum.photos](https://picsum.photos)
- Demo video stream — Big Buck Bunny (Blender Foundation)
- Icons — Material Icons Extended

---

> Built with ❤️ using Kotlin + Jetpack Compose for Android TV
