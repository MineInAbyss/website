plugins {
    application
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}


repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    implementation("me.dvyy:shocky:0.1.0")
    implementation("com.sksamuel.scrimage:scrimage-core:4.2.0")
    implementation("com.sksamuel.scrimage:scrimage-webp:4.2.0")
}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    main {
        kotlin.srcDirs("src")
    }
}

application {
    mainClass = "MainKt"
}
