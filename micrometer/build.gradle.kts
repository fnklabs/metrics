plugins {
    `java-library`
}

dependencies {
    api(project(":metrics-core"))

    compileOnly(libs.jetbrains.annotations)
    implementation(libs.micrometer.core)
    implementation(libs.micrometer.registry.jmx)

    testRuntimeOnly(libs.logback.classic)
}
