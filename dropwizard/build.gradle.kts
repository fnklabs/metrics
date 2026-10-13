plugins {
    `java-library`
}

dependencies {
    api(project(":metrics-core"))

    compileOnly(libs.jetbrains.annotations)
    implementation(libs.slf4j.api)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.dropwizard.metrics)
}
