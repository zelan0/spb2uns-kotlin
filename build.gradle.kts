plugins {
    kotlin("jvm") version "1.8.21"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.example"
version = "0.1.0"

application {
    mainClass.set("com.example.spb2uns.ApplicationKt")
}

repositories {
    mavenLocal()     // <– Check here first for locally built Tahu
    mavenCentral()   // <– Fallback if not found locally
}

dependencies {
    // Kotlin core
    implementation(kotlin("stdlib"))

    // Sparkplug B v3
    implementation("org.eclipse.tahu:tahu-core:1.0.14")

    // MQTT v5 client
    implementation("org.eclipse.paho:org.eclipse.paho.mqttv5.client:1.2.5")

    // AMQP client
    implementation("com.rabbitmq:amqp-client:5.25.0")

    // JSON / YAML parsing
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    // Coroutines
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    
    // Ktor HTTP + Prometheus metrics
    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3")
    implementation("io.ktor:ktor-server-metrics-micrometer:2.3.3")
    implementation("io.micrometer:micrometer-registry-prometheus:1.11.0")
    implementation("io.ktor:ktor-server-netty:2.3.3")
    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-status-pages:2.3.3")
    implementation("io.ktor:ktor-server-call-logging:2.3.3")

    //
    implementation("com.cirruslink.sparkplug:sparkplug_b_3_0_proto:1.0.3")


    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    



    // Testing
    testImplementation(kotlin("test"))
}

