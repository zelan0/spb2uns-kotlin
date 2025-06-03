package com.example.spb2uns
// [Full content already provided above — abbreviated here for brevity]
package com.example.spb2uns

import com.cirruslink.sparkplug.protobuf.PayloadOuterClass
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ParserService(
    private val config: Config,
    private val amqpClient: AmqpClient
) {
    fun handleMessage(topic: String, payload: ByteArray, userProperties: Map<String, String>) {
        // Filtering
        val filterKey = config.userPropertyFilterKey
        val filterValue = config.userPropertyFilterValue
        if (userProperties[filterKey] != filterValue) {
            logger.info { "Message filtered out by user property: $filterKey != $filterValue" }
            return
        }

        // Decode Sparkplug B
        val pl = try {
            PayloadOuterClass.Payload.parseFrom(payload)
        } catch (e: Exception) {
            logger.error(e) { "Failed to parse Sparkplug B payload" }
            return
        }

        // For each metric, republish as JSON
        pl.metricsList.forEach { metric ->
            val json = """{
                "metric": "${metric.name}",
                "value": ${metric.intValue},
                "timestamp": ${pl.timestamp}
            }""".trimIndent()

            // MQTT and AMQP topic structure (simplified)
            val segments = topic.split("/")
            if (segments.size < 5) return@forEach
            val unsMqttTopic = "UNS/${segments[1]}/${segments[2]}/${segments[3]}/${metric.name}"
            val unsAmqpKey = "UNS.${segments[1]}.${segments[2]}.${segments[3]}.${metric.name}"

            amqpClient.publish(unsAmqpKey, json)
            logger.info { "Republished: $unsAmqpKey -> $json" }
        }
    }
}
