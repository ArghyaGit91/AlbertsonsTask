plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.albertsonstask"
    compileSdk = 33



    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.albertsonstask"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
//        buildConfigField("String","BASE_API","https://dummyjson.com")
        buildConfigField("String", "BASE_API", "\"https://dummyjson.com/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    val navVersion = "2.6.0"
    val lifecycleVersion = "2.6.2"
    val roomVersion = "2.5.2"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    //jetpack navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //room db
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
//    implementation ("android.arch.persistence.room:runtime:$roomVersion")
//    annotationProcessor ("android.arch.persistence.room:compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("com.google.truth:truth:1.1.4")

    testImplementation ("org.mockito:mockito-core:2.24.5")
    // required if you want to use Mockito for Android tests
    androidTestImplementation ("org.mockito:mockito-android:2.24.5")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    androidTestImplementation ("android.arch.persistence.room:testing:1.0.0")

    testImplementation ("android.arch.core:core-testing:1.0.0")

    testImplementation ("io.mockk:mockk:1.12.0")
    androidTestImplementation ("io.mockk:mockk-android:1.12.0")


}

kapt {
    correctErrorTypes = true
}
