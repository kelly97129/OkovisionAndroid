# 📱 Okovision Android  
Application Android moderne reprenant l’interface d’Okovision (UI pixel-perfect) avec lecture chaudière en direct, historique consommation, synchronisation Home Assistant et gestion des palettes/sacs.

![Platform](https://img.shields.io/badge/platform-Android-green.svg)
![Compose](https://img.shields.io/badge/Jetpack-Compose-blue)
![Home Assistant](https://img.shields.io/badge/MQTT-HA-orange)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
![Version](https://img.shields.io/badge/version-2.3.5-blue)

---

## 🚀 Fonctionnalités

### 🔥 Lecture chaudière (Okofen / Okovision)
- Connexion directe HTTP/JSON (endpoints Okofen)
- Parsing automatique
- Valeurs en temps réel (T°, niveau silo, modulation, etc.)
- Mode hors-ligne (cache DataStore)

### 📦 Gestion du stock pellets
- Sacs restants  
- Palettes complètes + sacs individuels  
- Calcul du taux d’épuisement  
- Synchronisation Home Assistant via MQTT

### 📊 Graphiques & Historique
- MPAndroidChart (réels)
- Données stockées en Room Database
- Courbes journalières / hebdomadaires / mensuelles
- Export CSV (prévu v2.4+)

### 🏡 Home Assistant (MQTT + Auto-discovery)
- Publication des mesures :  
  `okovision/live/#`
- Historique :  
  `okovision/consumption`
- Auto-création des entités via MQTT Discovery  
  `homeassistant/sensor/okovision/.../config`

### 🎨 Interface moderne
- Reproduction exacte de l’UI Okovision  
- Dark Mode complet  
- Adaptative Icons  

---

## 🛠️ Technologies principales

| Catégorie | Choix |
|----------|-------|
| UI | Jetpack Compose + Material 3 |
| Stockage | Room + DataStore Preferences |
| Réseau | Retrofit + OkHttp |
| MQTT | Eclipse Paho |
| Graphiques | MPAndroidChart |
| Build | Gradle Kotlin DSL + Java 17 |
| CI/CD | GitHub Actions |

---

## 📁 Structure du projet

