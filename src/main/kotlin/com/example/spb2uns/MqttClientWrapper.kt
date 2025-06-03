package com.example.spb2uns
// [Full content already provided above — abbreviated here for brevity]
package com.example.spb2uns

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttClientWrapper(
    private val config: Config,
    private val onMessage: (topic: String, payload: ByteArray, userProperties: Map<String, String>) -> Unit
) {
    private val client = MqttClient(config.mqttBroker, MqttClient.generateClientId(), MemoryPersistence())

    fun start() {
        val connOpts = MqttConnectOptions().apply {
            isCleanSession = true
        }
        client.connect(connOpts)
        client.subscribe(config.mqttTopic, 1) { topic, message ->
            // Simulate MQTT 5 User Property using "properties" in Paho MQTT v3 as not natively supported.
            val userProps = mapOf(config.userPropertyFilterKey to config.userPropertyFilterValue) // Mock: must adapt if using real MQTT v5 client
            onMessage(topic, message.payload, userProps)
        }
        println("Subscribed to topic: ${config.mqttTopic}")
    }
}
