package project.fedorova.polyglotte;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class PrefActivity extends PreferenceActivity {
    SharedPreferences sPref;
    ListPreference nativeLanguage;
    private final static String LANGUAGE = "native language";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        nativeLanguage = (ListPreference) findPreference("nativeLanguage");
        nativeLanguage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                changeNativeLanguage();
                return true;
            }
        });
    }

    private void changeNativeLanguage() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(LANGUAGE, nativeLanguage.getValue());
        ed.commit();
        Toast.makeText(this, "Your native language changed", Toast.LENGTH_SHORT).show();
    }
}