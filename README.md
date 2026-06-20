# Android Background Camera Service (Android 14/15)

Complete Android project for background camera capture using foreground service.

## Quick Start

1. Clone repo: `git clone https://github.com/moh14amino-ui/ghmnmn.git`
2. Checkout branch: `git checkout android-background-camera`
3. Open in Android Studio
4. Connect Android device
5. Click Run button (green play icon)
6. Grant camera permission
7. Tap "Start Camera Service"

## Features

- Android 14/15 compatible
- Background camera with foreground service
- Image capture and storage
- Proper permission handling
- No APK build needed - runs directly from Android Studio

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/backgroundcamera/
│   │   ├── MainActivity.kt
│   │   └── CameraService.kt
│   ├── res/
│   │   └── layout/activity_main.xml
│   └── AndroidManifest.xml
└── build.gradle
```
