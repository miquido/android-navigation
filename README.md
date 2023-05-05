# Compose Navigator

Compose Navigator provides possibility to Control AndroidX NavController from View Model's. 

The library facilitates the usage of a single NavHost controlled by one point of truth - Navigator.  Provides a unified interface for providing results back between composables and receiving activity results.

## Features

View Model's can use [Navigator](/navigation-runtime/src/main/kotlin/com/miquido/android/navigation/Navigator.kt) interface provided by [navigation-runtime](/navigation-runtime) artifact to:
- navigate forward to `direction` with optional `NavOptions`
- navigate backward
- provide result data to previous backstack entry
- launch and receive activity results using `ActivityResultContract`

> **Warning**: library is designed to work with a single `NavHost`. Using nested `NavHost` is not tested and can cause unexpected behavior.

## Setup 

Compose Navigator is available via maven central.

1. Add dependency to to `navigation-runtime`

```groovy
dependencies {
    implementation "com.miquido.android.navigation:navigation-runtime:[version]"
}
```

2. Choose dependency injection integration used in your project `navigation-hilt` or `navigation-koin`

```groovy
dependencies {
    implementation "com.miquido.android.navigation:navigation-hilt:[version]"
    // OR
    implementation "com.miquido.android.navigation:navigation-koin:[version]"
}
```

> **Note**: that for multi-module project add this dependency only in application module.

3. Pass `NavController` to `NavigationHandler` to start processing commands sent by `Navigator` in View Models.

```kotlin
import com.miquido.android.navigation.handler.NavigationHandler

@Composable
fun AppRoot() {
    // ...
    val navController = rememberNavController()
    
    NavigationHandler(
        navController = navController
    )
    
    NavHost(
        navController = navController,
        route = "root",
        startDestination = "start"
    ) {
        // build your NavGraph
    }
}
```

4. Create View Model in composable using `navEntryViewModel` method.

```kotlin
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.viewmodel.navEntryViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel = navEntryViewModel()
) 
```

> **Note**: that all remaining dependency injection configuration (like using adding `@HiltViewModel` for Hilt or declaring module with `viewModelOf` for Koin) remains same as it was.

5. (**Optional**) Add integration with [Compose Destinations](https://composedestinations.rafaelcosta.xyz/)

```groovy
dependencies {
    implementation "com.miquido.android.navigation:navigation-destinations:[version]"
}
```

Using this artifact provides additional kotlin extension methods for:
- `Navigator` to avoid calling `Direction#rote` and `Route#route` when using provided API.
- `NavAction`'s to allow object creation using `Direction` / `Route` directly.

## Samples

The repository contains few samples - showcasing:
- koin integration [sample-koin](/sample-koin)
- **TBD** hilt integration [sample-hilt](/sample-hilt)
- **TBD** compose destination integration [sample-destinations](/sample-destinations)

## About

The library is in the beta stage, which means contains a core feature set commonly used.
Please do try it and open issues if you find any.

Any feedback and contributions are highly appreciated!

**If you like the library, consider starring and sharing it with your colleagues.**

---
## About Miquido
- [About](https://careers.miquido.com/about-us/)
- [Careers](https://careers.miquido.com/job-offers/)
- [Internship at Miquido](https://careers.miquido.com/students/)
