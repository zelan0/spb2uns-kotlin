package com.example.spb2uns
// [Full content already provided above — abbreviated here for brevity]
package com.example.spb2uns

data class Config(
    val mqttBroker: String,
    val mqttTopic: String,
    val amqpBroker: String,
    val amqpExchange: String,
    val userPropertyFilterKey: String,
    val userPropertyFilterValue: String
)

object AppConfig {
    val config = Config(
        mqttBroker = System.getenv("MQTT_BROKER") ?: "tcp://localhost:1883",
        mqttTopic = System.getenv("MQTT_TOPIC") ?: "spBv3.0/#",
        amqpBroker = System.getenv("AMQP_BROKER") ?: "amqp://guest:guest@localhost:5672/",
        amqpExchange = System.getenv("AMQP_EXCHANGE") ?: "uns_exchange",
        userPropertyFilterKey = System.getenv("MQTT_FILTER_KEY") ?: "region",
        userPropertyFilterValue = System.getenv("MQTT_FILTER_VALUE") ?: "PlantA"
    )
}
