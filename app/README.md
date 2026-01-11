# 🌦 WeatherApp – Jetpack Compose

A modern Android Weather application built using **Jetpack Compose**, **MVVM**, **Hilt**, **Room**, and **Retrofit**.  
The app supports **offline-first weather loading**, **3-day forecasts**, and a clean multi-screen architecture.

---

## 📱 Features

### 🏠 Home Screen
- Automatically loads **Bangalore weather**
- Displays **3-day forecast** in a **grid layout**
- Shows:
    - 📅 Date
    - 🌡 Temperature
    - ☁ Weather condition
    - 🌤 Weather icon
- Read-only screen (no search / refresh)

### 🔍 Search Screen
- Search weather by **city name**
- Manual **refresh button**
- Displays **3-day forecast**
- Fully supports **offline mode**

---

## 🧱 Architecture

The app follows **MVVM** with an **offline-first repository**.

