plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "ru.antonlm.common"
}

dependencies {
    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // chrome tabs
    implementation(libs.androidx.browser)

    // dagger
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    kapt(libs.daggerCompiler)
    kapt(libs.dagger.android.processor)
}