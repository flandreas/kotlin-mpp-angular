import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    "commonMainImplementation"(project(":domain"))
    nodeKotlin(project(":domain"))
}

plugins {
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

tasks {
    val shadowCreate by creating(ShadowJar::class) {
        manifest {
            attributes["Main-Class"] = "ch.flandreas.techstack.ui.FibonacciUI"
        }
        archiveClassifier.set("all")
        from(kotlin.jvm().compilations.getByName("main").output)
        configurations =
            mutableListOf(kotlin.jvm().compilations.getByName("main").compileDependencyFiles as Configuration)
    }

    val build by existing {
        dependsOn(shadowCreate)
    }

    val run by creating(JavaExec::class) {
        dependsOn(shadowCreate)
        classpath = files("$buildDir/libs/ui-$version-all.jar")
        main = "ch.flandreas.techstack.ui.FibonacciUI"
    }
}

val ngSrcDir = project.layout.projectDirectory.dir("js/src/angular")
val ngOutDir = project.layout.buildDirectory.dir("angular")

project.rootProject.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    nodeVersion = "13.2.0"
}

kt2ts {
    nodeSrcDirectory.set(ngSrcDir)
    nodeOutDirectory.set(ngOutDir)

    nodeBuildCommand.set(
        if (project.hasProperty("prod")) {
            listOf("ng", "build", "--prod", "--outputPath=${ngOutDir.get()}/dist")
        } else {
            listOf("ng", "build", "--outputPath=${ngOutDir.get()}/dist")
        }
    )
}

project.tasks.getByName("jvmProcessResources").dependsOn("nodeBuild")
kotlin {
    sourceSets {
        val jvmMain by getting {
            resources.srcDir(ngOutDir)
        }
    }
}

