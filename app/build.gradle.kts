plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.verbalvoyage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.verbalvoyage"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.github.parse-community.Parse-SDK-Android:bolts-tasks:4.3.0")
    implementation("com.github.parse-community.Parse-SDK-Android:parse:4.3.0")


// Google Translate API for translations
    implementation("com.google.cloud:google-cloud-translate:3.8.1") {
        exclude(group = "org.apache.httpcomponents")
        exclude(group = "org.json", module = "json")
    }
    annotationProcessor("com.google.cloud:google-cloud-translate:3.8.1")

// Google NLP API for entity recognition
    implementation(platform("com.google.cloud:libraries-bom:26.0.0"))
    implementation("com.google.cloud:google-cloud-language:3.8.1")
    implementation("io.grpc:grpc-okhttp:1.50.0")

// Parceler for passing objects
    implementation("org.parceler:parceler-api:1.1.13")
    annotationProcessor("org.parceler:parceler:1.1.13")

// SwipeRefreshLayout for home feed


// Apache Commons Text for escaping HTML / validating email
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("commons-validator:commons-validator:1.7") // Consider using a more actively maintained library like Apache Commons Lang Validator

// FlipView & CardView for flashcards
    implementation("eu.davidea:flipview:1.2.5") // Free for personal and open-source projects, commercial licenses available
    //implementation("androidx.cardview:cardview:1.5.0")
    implementation("com.yuyakaido.android:card-stack-view:2.3.5")

// CoordinatorLayout for collapsing search bar
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.5.0")

// DragSelectRecyclerView for word search
    implementation("com.github.MFlisar:DragSelectRecyclerView:0.4") // Consider using a more actively maintained library like androidx.recyclerview.selection
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.5.0") // Free for personal use, commercial licenses available

// ICU4J for internationalization
    implementation("com.ibm.icu:icu4j:73.1") // Free for open-source projects, commercial licenses available

// LikeButton for starring animation
   // Consider using a more actively maintained library like androidx.appcompat:appcompat
    implementation("com.mikepenz:materialdrawer:8.8.0") // Free for personal and open-source projects, commercial licenses available

// Glide for loading images
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

// ImageSupport for editing profile picture
    implementation("io.github.nikartm:image-support:2.3.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0") // or any compatible version
    implementation("com.codepath.asynchttpclient:async-http-client:2.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("com.github.michaelflisar:dragselectrecyclerview:0.0.3") // Replace x.y.z with the specific version
}