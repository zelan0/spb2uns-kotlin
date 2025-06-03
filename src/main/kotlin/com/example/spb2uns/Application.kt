package com.example.spb2uns

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*

fun main() {
    val config = AppConfig.config
    val amqpClient = AmqpClient(config)
    val parser = ParserService(config, amqpClient)
    val mqtt = MqttClientWrapper(config) { topic, payload, userProps ->
        parser.handleMessage(topic, payload, userProps)
    }

    // Start HTTP server for /health endpoint
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/health") {
                call.respond(HttpStatusCode.OK, "OK")
            }
        }
    }.start(wait = false)

    // Start MQTT subscriber (blocking call)
    mqtt.start()
}
