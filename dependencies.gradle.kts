object versions {
    val ver = mapOf(
        "kotlin" to "1.3.41",
        "coroutines" to "1.3.0-RC2",
        "log4j" to "2.11.2"
    )
    val depend = mapOf(
        "kotlin" to "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${ver["kotlin"]}",
        "reflect" to "org.jetbrains.kotlin:kotlin-reflect:${ver["kotlin"]}",
        "coroutines" to "org.jetbrains.kotlinx:kotlinx-coroutines-core:${ver["coroutines"]}",

        "ktest" to "org.jetbrains.kotlin:kotlin-test:${ver["kotlin"]}",
        "kunit" to "org.jetbrains.kotlin:kotlin-test-junit:${ver["kotlin"]}",

        "log-core" to "org.apache.logging.log4j:log4j-core:${ver["log4j"]}",
        "log-api" to "org.apache.logging.log4j:log4j-api:${ver["log4j"]}"

    )
}

project.extra.set("versions", versions.depend)