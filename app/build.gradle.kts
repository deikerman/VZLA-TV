import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(mylibs.plugins.androidApplication)
    alias(mylibs.plugins.kotlinAndroid)
}

android {
    namespace = "com.example.vzlatv"
    compileSdk = 36

    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("user.home") + "/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    defaultConfig {
        applicationId = "com.example.vzlatv"
        minSdk = 21
        targetSdk = 36
        versionCode = 3
        versionName = "1.2"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

dependencies {
    // AndroidX (ya usando version catalog)
    implementation(mylibs.core.ktx)
    implementation(mylibs.appcompat)
    implementation(mylibs.material)
    implementation(mylibs.recyclerview)

    // ExoPlayer
    implementation(mylibs.exoplayer)

    // Networking
    implementation(mylibs.okhttp)

    // Coroutines
    implementation(mylibs.coroutinesAndroid)
}
