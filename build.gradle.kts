import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform") version "1.3.61" apply false
    id("net.akehurst.kotlin.kt2ts") version("1.5.0") apply false
}

allprojects {

    repositories {
        mavenCentral()
        jcenter()
    }

    val version_project: String by project
    val group_project = "${rootProject.name}"

    group = group_project
    version = version_project

    buildDir = File(rootProject.projectDir, ".gradle-build/${project.name}")
}

val mockkVersion: String by extra

subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "net.akehurst.kotlin.kt2ts")

    configure<KotlinMultiplatformExtension> {
        jvm() {
            // by default kotlin uses JavaVersion 1.6
            val main by compilations.getting {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_8.toString()
                    freeCompilerArgs = listOf("-Xinline-classes")
                }
            }
            val test by compilations.getting {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_8.toString()
                }
            }
        }
        js() {
            browser()
        }

        sourceSets {
            val commonMain by getting {
                kotlin.srcDir("common/src/main")
                resources.srcDir("common/rsc")
                dependencies { implementation(kotlin("stdlib")) }
            }
            val commonTest by getting {
                kotlin.srcDir("common/src/test")
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-annotations-common"))
                    implementation("io.mockk:mockk-common:$mockkVersion")
                }
            }
            val jvmMain by getting {
                kotlin.srcDir("jvm/src/main")
                dependencies { implementation(kotlin("stdlib-jdk8")) }
            }
            val jvmTest by getting {
                kotlin.srcDir("jvm/src/test")
                dependencies {
                    implementation(kotlin("test-junit"))
                    implementation("io.mockk:mockk:$mockkVersion")
                }
            }
            val jsMain by getting {
                kotlin.srcDir("js/src/kotlin/main")
                dependencies { implementation(kotlin("stdlib-js")) }
            }
            val jsTest by getting {
                kotlin.srcDir("js/src/kotlin/test")
                dependencies {
                    implementation(kotlin("test-js"))
                    implementation("io.mockk:mockk-js:1.7.17")
                }
            }

            // Workaround for bug https://youtrack.jetbrains.com/issue/KT -24463:
            // Copy all resource files to the build directory used by IDEA run configuration
            tasks {
                val deployResources by creating(Copy::class) {
                    from(listOf(commonMain.resources, jvmMain.resources)) {
                        include("**/*.properties")
                    }
                    into("${buildDir.absolutePath}/classes/kotlin/jvm/main")
                }
                getByName("jvmMainClasses") {
                    dependsOn(deployResources)
                }
            }
        }
    }
}
