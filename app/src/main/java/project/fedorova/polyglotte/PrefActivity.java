package project.fedorova.polyglotte;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import project.fedorova.polyglotte.data.PreferenceVars;

public class PrefActivity extends PreferenceActivity {
    PreferenceVars preferenceVars = PreferenceVars.getInstance();
    ListPreference nativeLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        nativeLanguage = (ListPreference) findPreference("nativeLanguage");
        nativeLanguage.setOnPreferenceChangeListener((preference, newValue) -> {
            changeNativeLanguage();
            return true;
        });
    }

    private void changeNativeLanguage() {
        preferenceVars.setNativeLang(nativeLanguage.getValue());
        preferenceVars.setNativeLangChanged(true);
        Toast.makeText(this, "Your native language changed to" + nativeLanguage.getValue(), Toast.LENGTH_SHORT).show();
    }
}