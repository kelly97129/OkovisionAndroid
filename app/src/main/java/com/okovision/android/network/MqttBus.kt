package com.okovision.android.network

import com.okovision.android.data.SettingsStore

object MqttBus {
    private var helper: MqttHelper? = null

    fun ensureConnected() {
        val s = SettingsStore.settings.value
        if (helper == null) {
            helper = MqttHelper(s.mqttBroker)
            runCatching { helper?.connect(s.mqttUser, s.mqttPass) }
        }
    }

    fun publish(topic: String, payload: String, retain: Boolean = true, qos: Int = 1) {
        ensureConnected()
        helper?.publish(topic, payload, retain, qos)
    }
}