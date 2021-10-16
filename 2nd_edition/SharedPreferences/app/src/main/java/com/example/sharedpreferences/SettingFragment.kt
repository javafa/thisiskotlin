package com.example.sharedpreferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

}