import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    alias(libs.plugins.vanniktech.maven.publish) apply false
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "idea")
    apply(plugin = "com.vanniktech.maven.publish")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        toolchain {
            version = JavaVersion.VERSION_25
        }
    }

    configure<org.gradle.plugins.ide.idea.model.IdeaModel> {
        module {
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    dependencies {
        "testImplementation"(platform(rootProject.libs.junit.bom))
        "testImplementation"(rootProject.libs.junit.jupiter)
        "testRuntimeOnly"(rootProject.libs.junit.platform.launcher)
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    configure<MavenPublishBaseExtension> {
        publishToMavenCentral(automaticRelease = true)

        signAllPublications()

        pom {
            name = "metrics"
            description = "utils for working with metrics"
            url = "https://github.com/fnklabs/metrics"
            licenses {
                license {
                    name = "The Apache License, Version 2.0"
                    url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                }
            }
            developers {
                developer {
                    id = "a.alimov"
                    name = "Alisher Alimov"
                    email = "alisher.alimov@fnklabs.com"
                }
            }
            scm {
                connection = "scm:git:git@github.com:fnklabs/metrics.git"
                developerConnection = "scm:git:ssh://git@github.com/fnklabs/metrics.git"
                url = "https://github.com/fnklabs/metrics"
            }
        }
    }
}
