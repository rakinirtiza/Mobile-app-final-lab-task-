package com.university.usersettings

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsViewerActivity : AppCompatActivity() {

    private lateinit var tvSavedData: TextView
    private lateinit var btnBack: Button
    private lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_viewer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Saved Settings"

        tvSavedData = findViewById(R.id.tvSavedData)
        btnBack = findViewById(R.id.btnBack)
        btnEdit = findViewById(R.id.btnEdit)

        showSavedSettings()

        btnBack.setOnClickListener {
            finish()
        }

        btnEdit.setOnClickListener {
            finish()
        }
    }

    private fun showSavedSettings() {
        val prefs = getSharedPreferences(MainActivity.PREF_APP, Context.MODE_PRIVATE)

        if (!prefs.contains(MainActivity.KEY_LAST_SAVED)) {
            tvSavedData.text = "No settings saved. Go back and save your preferences."
            return
        }

        val name = prefs.getString(MainActivity.KEY_STUDENT_NAME, "Not set")
        val theme = prefs.getString(MainActivity.KEY_THEME, "light")
        val notifications = prefs.getBoolean(MainActivity.KEY_NOTIFICATIONS, true)
        val language = prefs.getString(MainActivity.KEY_LANGUAGE, "English")
        val fontSize = prefs.getInt(MainActivity.KEY_FONT_SIZE, 16)
        val lastSaved = prefs.getLong(MainActivity.KEY_LAST_SAVED, 0L)

        val formattedDate = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date(lastSaved))

        tvSavedData.text = """
            Student Name: $name
            
            Theme: $theme
            
            Notifications: $notifications
            
            Language: $language
            
            Font Size: ${fontSize}sp
            
            Last Saved: $formattedDate
        """.trimIndent()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}