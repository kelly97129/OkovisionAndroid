package com.okovision.android.network

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*

class MqttHelper(broker: String, clientId: String = "okovision-" + UUID.randomUUID().toString().take(8)) {
    private val client = MqttClient(broker, clientId, null)

    fun connect(username: String? = null, password: String? = null) {
        val opts = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
            if (username != null && password != null) {
                userName = username
                this.password = password.toCharArray()
            }
        }
        if (!client.isConnected) client.connect(opts)
    }

    fun publish(topic: String, payload: String, retain: Boolean = true, qos: Int = 1) {
        if (!client.isConnected) return
        val msg = MqttMessage(payload.toByteArray()).apply { this.qos = qos; isRetained = retain }
        client.publish(topic, msg)
    }

    fun disconnect() { if (client.isConnected) client.disconnect() }
}