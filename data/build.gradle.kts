plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "ru.antonlm.data"
}

dependencies {
    // modules
    implementation(project(":common"))

    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // dagger
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    kapt(libs.daggerCompiler)
    kapt(libs.dagger.android.processor)

    // retrofit + gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
}