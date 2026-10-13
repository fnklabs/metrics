pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "metrics-parent"

include("metrics-core")
include("metrics-dropwizard")
include("metrics-micrometer")

project(":metrics-core").projectDir = file("core")
project(":metrics-dropwizard").projectDir = file("dropwizard")
project(":metrics-micrometer").projectDir = file("micrometer")


dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}