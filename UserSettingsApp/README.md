# UserSettingsApp

## Lab Task 11: Shared Preferences — User Settings Manager

This is an Android application developed using **Kotlin**. The app demonstrates how to use **SharedPreferences** to save, read, update, clear, and persist user settings across app restarts.

## Features

- Save student name
- Select theme: Light, Dark, System Default
- Enable/disable notifications
- Select language: English, Bangla, Arabic, French
- Change font size from 12sp to 24sp
- View saved settings with last saved time
- Reset settings to default
- Save student profile information
- Show welcome message after saving profile

## SharedPreferences

The app uses two SharedPreferences files:

- **AppSettings** — stores theme, notifications, language, font size, last saved time, and student name
- **ProfilePrefs** — stores student ID, full name, department, year, and email

## Activities

- **MainActivity** — Settings dashboard
- **SettingsViewerActivity** — Displays saved settings
- **ProfileActivity** — Saves student profile data

