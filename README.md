# The Movie DB
![Cover](/images/cover.png "Cover")

The Movie DB app is an implementation of [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). It is a native Android app built using Jetpack Compose with modern Android development using Hilt, Coroutines, Flow, Jetpack, and Material Design.

## 📲 Download APK
Download the APK from this [Link](https://bit.ly/49kd5fC).

## 🛠 Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack:
  - [Compose](https://developer.android.com/jetpack/compose): Android’s modern toolkit for building native UI.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): UI related data holder and lifecycle aware.
  - [App Startup](https://developer.android.com/topic/libraries/app-startup): Provides a straightforward, performant way to initialize components at application startup.
  - [Hilt Navigation](https://developer.android.com/jetpack/compose/libraries#hilt-navigation): For navigating screens and injecting dependencies.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
- [Ktor](https://github.com/ktorio/ktor): Kotlin-based framework for building efficient HTTP clients in Kotlin applications.
- [SQLDelight](https://github.com/cashapp/sqldelight): Generates Kotlin APIs from SQL statements for seamless database integration.
- [Coil](https://github.com/coil-kt/coil): Image loading library for loading images from network.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building ripple animation, and CardView.
- [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API.

## Architecture
![CleanArchitecture-Android](/images/AndroidCleanArchitecture.png "AndroidCleanArchitecture")

The project is based on the Clean Architecture and the MVVM pattern. It uses the [Google's ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) as a UI state holder to take advantage of its [benefits](https://developer.android.com/topic/libraries/architecture/viewmodel#viewmodel-benefits) like persistence, view lifecycle scooping, and SavedStateHandle.

With this loosely coupled architecture, you can increase the reusability of components and scalability of your app.

### UI Layer
The UI layer consists of Jetpack Compose UI elements to configure screens that interact with users and the Presentation layer by sending actions to it and observing it's data changes.

### Presentation Layer
The Presentation layer contains [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) that present the app states and restores data when configuration changes. The ViewModels interact with the Domain layer by Use Cases.

### Domain Layer
- Use Cases: Act as an abstraction layer between the Domain layer and the Presentation layer. Use Cases encapsulate the logic required to perform tasks like retrieving movies list or submit movie ratings.
- Repositories: Interfaces that define the contract for accessing and managing data from external data sources(local DB, and remote server). The Domain layer defines the repository interfaces, while the actual implementation is provided in the Data layer. This separation allows the Domain layer to remain agnostic to the data source.
- Domain Models: Plain Kotlin classes that represent the core data structures and business objects of the app. These objects encapsulate the data and behavior that are central to the app's domain.

### Data Layer
The Data layer contains the Repositories implementation, it queries data from the local database and requests remote data from the network. It follows the [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.