package com.seenu.dev.android.november25

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.seenu.dev.android.november25.screens.GlobalBlackFridayDeals
import com.seenu.dev.android.november25.theme.NovemberTheme

class GlobalBlackFridayDealsActivity : ComponentActivity() {

    private lateinit var appPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appPref = getSharedPreferences("app_pref", MODE_PRIVATE)
        setLanguage()
        setContent {
            NovemberTheme {
                GlobalBlackFridayDeals()
            }
        }
    }

    private fun setLanguage() {
        val lang = appPref.getString("language", "ENGLISH") ?: "ENGLISH"
        val languageCode = when (lang) {
            "ENGLISH" -> "en"
            "SPANISH" -> "es"
            "ARABIC" -> "ar"
            else -> "en"
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }

}