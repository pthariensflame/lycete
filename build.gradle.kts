@file:Suppress("SuspiciousCollectionReassignment")

//import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.4-M2"
    kotlin("kapt") version "1.4-M2"
    id("io.gitlab.arturbosch.detekt") version "1.9.1"
//    id("org.jetbrains.dokka") version "0.11.0-dev-45"
    application
    `maven-publish`
    idea
}

val kotlinVersion: String by project
val kotlinXCoroutinesVersion: String by project
val jetbrainsAnnotationsVersion: String by project
val detektVersion: String by project
val graalVMVersion: String by project
val junit5Version: String by project
val kotestVersion: String by project
val icuVersion: String by project

group = "com.pthariensflame.lycete"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    api(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    testApi(platform("org.junit:junit-bom:$junit5Version"))
    api(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$kotlinXCoroutinesVersion"))
    constraints {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.7") // FIXME
        api("junit:junit:4.13")
    }

    kapt("org.graalvm.truffle", "truffle-dsl-processor", graalVMVersion)
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

    api(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    api("org.jetbrains", "annotations", jetbrainsAnnotationsVersion)
    api("org.graalvm.truffle", "truffle-api", graalVMVersion)
    api("org.graalvm.sdk", "graal-sdk", graalVMVersion)
    api("org.graalvm.tools", "lsp_api", graalVMVersion)
    implementation("com.ibm.icu", "icu4j", icuVersion)

    testApi(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest", "kotest-runner-junit5-jvm", kotestVersion)
    testApi("io.kotest", "kotest-assertions-core-jvm", kotestVersion)
    testApi("io.kotest", "kotest-property-jvm", kotestVersion)
    testApi("org.graalvm.truffle", "truffle-tck", graalVMVersion)
}

java {
//    modularity.inferModulePath.set(true)
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApiWarning()
}

kapt {
    correctErrorTypes = true
    includeCompileClasspath = false
}

detekt {
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

application {
    mainClassName = "com.pthariensflame.lycete.execution.MainKt"
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    withType<JavaCompile>().configureEach {
        options.apply {
            compilerArgs.addAll(arrayOf())
        }
    }

    withType<KotlinCompile>().configureEach {
        usePreciseJavaTracking = true
        kotlinOptions {
            languageVersion = "1.4"
            apiVersion = "1.4"
            javaParameters = true
            jvmTarget = "11"
            freeCompilerArgs += arrayOf(
                    "-progressive"
                                       )
        }
    }

//    withType<DokkaTask>().configureEach {
//        outputFormat = "gfm"
//        configuration {
//            jdkVersion = 11
//            sourceLink {
//                path = "./"
//                url = "https://github.com/pthariensflame/lycete/blob/master"
//                lineSuffix = "#L"
//            }
////            reportUndocumented = true
//        }
//    }
}
