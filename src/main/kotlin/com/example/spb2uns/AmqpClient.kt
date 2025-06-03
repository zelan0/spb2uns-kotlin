package com.example.spb2uns

import com.rabbitmq.client.ConnectionFactory

class AmqpClient(private val config: Config) {
    private val factory = ConnectionFactory().apply { setUri(config.amqpBroker) }
    private val connection = factory.newConnection()
    private val channel = connection.createChannel()

    fun publish(routingKey: String, message: String) {
        channel.basicPublish(config.amqpExchange, routingKey, null, message.toByteArray())
    }

    fun close() {
        channel.close()
        connection.close()
    }
}
