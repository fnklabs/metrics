plugins {
    `java-library`
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
    implementation(libs.typesafe.config)
    implementation(libs.slf4j.api)
}
