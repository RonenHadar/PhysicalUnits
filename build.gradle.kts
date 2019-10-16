
//
apply(from="dependencies.gradle.kts")

plugins {
    //kotlin("jvm") version "1.3.50" apply false
    kotlin("jvm") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.18" apply false
    //java
}

group = "org.armya"

//subprojects {
    version="0.1.1" // define the global version of the project and all it's subprojects
//}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.12")
}