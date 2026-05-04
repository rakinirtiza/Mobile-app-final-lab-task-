package com.university.usersettings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var rgTheme: RadioGroup
    private lateinit var rbLight: RadioButton
    private lateinit var rbDark: RadioButton
    private lateinit var rbSystem: RadioButton
    private lateinit var switchNotifications: SwitchCompat
    private lateinit var spinnerLanguage: Spinner
    private lateinit var seekBarFontSize: SeekBar
    private lateinit var tvFontSize: TextView
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private lateinit var btnViewSaved: Button
    private lateinit var btnProfile: Button

    private val languages = arrayOf("English", "Bangla", "Arabic", "French")

    companion object {
        const val PREF_APP = "AppSettings"

        const val KEY_THEME = "KEY_THEME"
        const val KEY_NOTIFICATIONS = "KEY_NOTIFICATIONS"
        const val KEY_LANGUAGE = "KEY_LANGUAGE"
        const val KEY_FONT_SIZE = "KEY_FONT_SIZE"
        const val KEY_LAST_SAVED = "KEY_LAST_SAVED"
        const val KEY_STUDENT_NAME = "KEY_STUDENT_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupSpinner()
        setupSeekBar()
        setupButtons()
        restoreSettings()
    }

    override fun onResume() {
        super.onResume()
        restoreSettings()
    }

    private fun bindViews() {
        etStudentName = findViewById(R.id.etStudentName)
        rgTheme = findViewById(R.id.rgTheme)
        rbLight = findViewById(R.id.rbLight)
        rbDark = findViewById(R.id.rbDark)
        rbSystem = findViewById(R.id.rbSystem)
        switchNotifications = findViewById(R.id.switchNotifications)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        seekBarFontSize = findViewById(R.id.seekBarFontSize)
        tvFontSize = findViewById(R.id.tvFontSize)
        btnSave = findViewById(R.id.btnSave)
        btnReset = findViewById(R.id.btnReset)
        btnViewSaved = findViewById(R.id.btnViewSaved)
        btnProfile = findViewById(R.id.btnProfile)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            languages
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter
    }

    private fun setupSeekBar() {
        seekBarFontSize.max = 12

        seekBarFontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val fontSize = progress + 12
                tvFontSize.text = "Font Size: ${fontSize}sp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupButtons() {
        btnSave.setOnClickListener {
            saveSettings()
        }

        btnReset.setOnClickListener {
            resetSettings()
        }

        btnViewSaved.setOnClickListener {
            val intent = Intent(this, SettingsViewerActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveSettings() {
        val selectedTheme = when (rgTheme.checkedRadioButtonId) {
            R.id.rbDark -> "dark"
            R.id.rbSystem -> "system"
            else -> "light"
        }

        val selectedLanguage = spinnerLanguage.selectedItem.toString()
        val fontSize = seekBarFontSize.progress + 12
        val studentName = etStudentName.text.toString().trim()

        val sharedPref = getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)

        sharedPref.edit()
            .putString(KEY_THEME, selectedTheme)
            .putBoolean(KEY_NOTIFICATIONS, switchNotifications.isChecked)
            .putString(KEY_LANGUAGE, selectedLanguage)
            .putInt(KEY_FONT_SIZE, fontSize)
            .putLong(KEY_LAST_SAVED, System.currentTimeMillis())
            .putString(KEY_STUDENT_NAME, studentName)
            .apply()

        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun restoreSettings() {
        val prefs = getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)

        val theme = prefs.getString(KEY_THEME, "light")
        val notifications = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        val language = prefs.getString(KEY_LANGUAGE, "English")
        val fontSize = prefs.getInt(KEY_FONT_SIZE, 16)
        val studentName = prefs.getString(KEY_STUDENT_NAME, "")

        etStudentName.setText(studentName)

        when (theme) {
            "dark" -> rbDark.isChecked = true
            "system" -> rbSystem.isChecked = true
            else -> rbLight.isChecked = true
        }

        switchNotifications.isChecked = notifications

        val langIndex = languages.indexOf(language)
        if (langIndex >= 0) {
            spinnerLanguage.setSelection(langIndex)
        }

        seekBarFontSize.progress = fontSize - 12
        tvFontSize.text = "Font Size: ${fontSize}sp"
    }

    private fun resetSettings() {
        val prefs = getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        etStudentName.setText("")
        rbLight.isChecked = true
        switchNotifications.isChecked = true
        spinnerLanguage.setSelection(0)
        seekBarFontSize.progress = 4
        tvFontSize.text = "Font Size: 16sp"

        Toast.makeText(this, "Settings reset to default", Toast.LENGTH_SHORT).show()
    }
}