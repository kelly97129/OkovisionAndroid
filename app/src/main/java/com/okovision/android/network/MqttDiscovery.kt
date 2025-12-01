package com.okovision.android.network

object MqttDiscovery {
    private fun configTopic(id: String) = "homeassistant/sensor/okovision/$id/config"

    private fun publishConfig(id: String, name: String, unit: String?, stateTopic: String, valueTemplate: String, deviceClass: String? = null, icon: String? = null) {
        val device = """
            "device": {
              "identifiers": ["okovision_app"],
              "name": "Okovision Android",
              "manufacturer": "Okovision",
              "model": "Android"
            }
        """.trimIndent()
        val b = StringBuilder().apply {
            append("{")
            append("\"name\":\"").append(name).append("\",")
            append("\"unique_id\":\"okovision_").append(id).append("\",")
            append("\"state_topic\":\"").append(stateTopic).append("\",")
            append("\"value_template\":\"").append(valueTemplate.replace("\"","\\\"")).append("\",")
            unit?.let { append("\"unit_of_measurement\":\"").append(it).append("\",") }
            deviceClass?.let { append("\"device_class\":\"").append(it).append("\",") }
            icon?.let { append("\"icon\":\"").append(it).append("\",") }
            append(device)
            append("}")
        }.toString()
        MqttBus.publish(configTopic(id), b, retain = true, qos = 1)
    }

    fun publishAll() {
        MqttBus.ensureConnected()
        publishConfig(
            id = "water_temp",
            name = "Chaudière - Température eau",
            unit = "°C",
            stateTopic = "okovision/live",
            valueTemplate = "{{ value_json.waterTempC }}",
            deviceClass = "temperature"
        )
        publishConfig(
            id = "pellet_level",
            name = "Chaudière - Niveau pellets",
            unit = "%",
            stateTopic = "okovision/live",
            valueTemplate = "{{ value_json.pelletLevelPct }}"
        )
        publishConfig(
            id = "daily_consumption",
            name = "Pellets - Conso jour",
            unit = "kg",
            stateTopic = "okovision/consumption",
            valueTemplate = "{{ value_json.kg }}"
        )
        publishConfig(
            id = "sacks_left",
            name = "Pellets - Sacs restants",
            unit = "sacs",
            stateTopic = "okovision/stocks",
            valueTemplate = "{{ value_json.sacksLeft }}"
        )
    }
}