package project.fedorova.polyglotte;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class PrefActivity extends PreferenceActivity {
    ListPreference nativeLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        nativeLanguage = (ListPreference) findPreference(getString(R.string.nativeLanguage));
        nativeLanguage.setOnPreferenceChangeListener((preference, newValue) -> {
            changeNativeLanguage((String) newValue);
            return true;
        });
    }

    private void changeNativeLanguage(String newValue) {
        Intent intent = getIntent();
        Toast.makeText(this, getString(R.string.msg_native_lang_changed) + newValue, Toast.LENGTH_SHORT).show();
        intent.putExtra(getString(R.string.language), newValue);
        setResult(RESULT_OK, intent);
    }
}