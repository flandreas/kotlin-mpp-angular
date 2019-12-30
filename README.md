# kotlin-multiplatform-angular

This project is a playground for exploring how to use Kotlin for building a multi-platform application that contains
 JVM and Angular UIs.

The domain logic calculates Fibonacci numbers and is by intention as simple as possible.

Links:
* Multiplatform setup: https://github.com/dhakehurst/example.kotlin.angular
* Kotlin and Typescript integration: https://www.itemis.com/en/kotlin-typescript-integration/
 
## Environment

* Developement with IntelliJ IDEA 2019.3
* Gradle project with multiple subprojects
* Kotlin multiplatform setup for JVM and JS targets
* mockk for mocking in tests 
* Entire domain logic written in Kotlin
* JVM Swing UI built with ShadowJar and started by gradle "run" task
* Angular UI using Kotlin domain logic in the browser

## Usage

### Run JVM application

In a console (using the built shadow JAR):
```
java -jar .gradle-build/ui/libs/ui-1.0.0-all.jar
```

Or with the gradle `run` task (also using the shadow JAR):

```
gradlew :ui:run
```

Or with an IntelliJ IDEA run configuration: Navigate in IntelliJ IDEA to ../ui/jvm/src/main/ch/flandreas/techstack/ui
/FibonacciUI and execute "Run FibonacciUI" from the popup menu, which will generate a new run configuration.

### Run Angular App

In a console:
```
cd ui/src/angular
ng serve --open
```

## Solved issues

1. Resources are not part of IntelliJ Kotlin (JVM) run configuration classpath. See https://youtrack.jetbrains.com
/issue/KT-24463. Use gradle task to copy the resources to the appropriate build directory.
2. The "application" plugin can't be used, because the "main" source set is hardcoded in ApplicationPlugin, but the
 source set of the artifact is "jvmMain". See https://github.com/gradle/gradle/issues/8113. Hence, the ShadowJar
  plugin can't be used based on the application plugin, but must be configured separately. The same applies to
   running the Java application.

## Open issues

### node doesn't find custom modules

The custom domain module as well as the Kotlin JS runtime libraries are assembled in the `node_modules` subdirectory
 of the build folder, where they are not found by `ng serve`, because it runs on its own local `node_modules` directory.
 
Current workaround: Symlinks from `ui/js/src/angular/node_modules` to `kotlin` and `techstack-domain` directories in
 the build folder.

## Future extensions

1. Include backend REST endpoint
