MQTT Discovery:
- Sensors créés automatiquement :
  homeassistant/sensor/okovision/water_temp/config      -> okovision/live  {{ value_json.waterTempC }}
  homeassistant/sensor/okovision/pellet_level/config    -> okovision/live  {{ value_json.pelletLevelPct }}
  homeassistant/sensor/okovision/daily_consumption/config -> okovision/consumption {{ value_json.kg }}
  homeassistant/sensor/okovision/sacks_left/config      -> okovision/stocks {{ value_json.sacksLeft }}

États publiés (retain=true) :
  okovision/live          {"waterTempC":64.2,"pelletLevelPct":58,"status":"heating"}
  okovision/consumption   {"date":"YYYY-MM-DD","kg":12.3}
  okovision/stocks        {"sacksLeft":50,"bagKg":15,"pallet":72}