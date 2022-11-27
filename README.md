A Custom Calendar Library based on Kotlin MVVM architecture and compose

[![](https://jitpack.io/v/winniecake/custom-calendar.svg)](https://jitpack.io/#winniecake/custom-calendar)

## Features
1. There are three calendar view : Month/One Week/Two Week
2. Swiping left or right to change calendar page
3. Providing three calendar theme : EarthTones/Light/Dark
4. Setting which day is the start day of week

## Tech/Tools
* [Kotlin](https://kotlinlang.org/) 100% coverage
* [Jetpack](https://developer.android.com/jetpack)
    * [Compose](https://developer.android.com/jetpack/compose) 
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
    * [MutableState](https://developer.android.com/jetpack/compose/state)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) for dependency injection

## Demo
<img src="readme/calendar_mode_change.gif" width="256" height="540" hspace="10" vspace="10">  <img src="readme/calendar_theme_change.gif" width="256" height="540" hspace="10" vspace="10">  <img src="readme/calendar_start_day_of_week_change.gif" width="256" height="540" hspace="10" vspace="10">

## How to use
1. Add the JitPack repository to settings gradle
```gradle
dependencyResolutionManagement {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.winniecake:custom-calendar:1.0.2'
}
```
3. Add WCalendar composable
```kotlin
WCalendar(
   modifier = Modifier.fillMaxWidth().wrapContentHeight(),
   type = WCalendarPeriod.Month,
   startDayOfWeek = WCalendarStartDayOfWeek.SUNDAY,
   theme = WCalendarThemeName.EarthTones,
   onDaySelected = { date -> }
)
```

