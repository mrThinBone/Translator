This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

https://github.com/icerockdev/moko-mvvm
https://cashapp.github.io/sqldelight/1.5.5/multiplatform_sqlite/
#https://github.com/coil-kt/coil/issues/842
https://insert-koin.io/docs/reference/koin-mp/kmp/
https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-images-resources.html#resource-usage
https://medium.com/@j.c.moreirapinto/simplifying-cross-platform-app-development-dependency-injection-with-koin-in-compose-multiplatform-f77595396fbc

navigation: https://voyager.adriel.cafe/
viewModel for iOS: https://voyager.adriel.cafe/screenmodel

todo:
_ speech to text
_ translate api
_ show toast (string resource?)
_ testing