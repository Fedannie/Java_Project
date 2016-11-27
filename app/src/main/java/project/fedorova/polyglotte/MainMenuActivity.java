package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.NoSuchElementException;

import project.fedorova.polyglotte.data.DictList;
import project.fedorova.polyglotte.data.PreferenceVars;

public class MainMenuActivity extends Activity implements View.OnClickListener {
    SharedPreferences sPref;
    Button btnExercise;
    Button btnPref;
    Button btnDict;
    Button btnPhrase;
    Button btnTrans;
    Button addDictBtn;
    Spinner selectDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnExercise = (Button) findViewById(R.id.exerciseButton);
        btnExercise.setOnClickListener(this);

        btnPref = (Button) findViewById(R.id.preferencesButton);
        btnPref.setOnClickListener(this);

        btnDict = (Button) findViewById(R.id.dictionaryButton);
        btnDict.setOnClickListener(this);

        btnPhrase = (Button) findViewById(R.id.phrasebookButton);
        btnPhrase.setOnClickListener(this);

        btnTrans = (Button) findViewById(R.id.translatorButton);
        btnTrans.setOnClickListener(this);

        addDictBtn = (Button) findViewById(R.id.addDictButton);
        addDictBtn.setOnClickListener(this);

        selectDict = (Spinner) findViewById(R.id.dictSpinner);
        selectDict.setPrompt("Your dictionaries");
        setSpinner();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSpinner();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.exerciseButton):
                Intent intentE = new Intent(this, ExerciseActivity.class);
                startActivity(intentE);
                break;
            case (R.id.preferencesButton):
                Intent intentP = new Intent(this, PrefActivity.class);
                startActivity(intentP);
                break;
            case (R.id.dictionaryButton):
                Intent intentD = new Intent(this, DictActivity.class);
                startActivity(intentD);
                break;
            case (R.id.phrasebookButton):
                Intent intentPh = new Intent(this, PhraseActivity.class);
                startActivity(intentPh);
                break;
            case (R.id.translatorButton):
                Intent intentTr = new Intent(this, TranslatorActivity.class);
                startActivity(intentTr);
                break;
            case (R.id.addDictButton):
                showPopupAddDict();
                break;
            default:
                break;
        }
    }

    private int getLastDict() throws NoSuchElementException{
        sPref = getPreferences(MODE_PRIVATE);
        String language = sPref.getString(PreferenceVars.dictLanguage, "");
        String[] dictionaries = DictList.getDictList();
        for (int i = 0; i < dictionaries.length; i++) {
            if (dictionaries[i].equals(language)) {
                return i;
            }
        }
        return 0;
    }

    private void showPopupAddDict() {
        Intent intentTr = new Intent(this, PopUpAddDict.class);
        startActivity(intentTr);
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DictList.getDictList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDict.setAdapter(adapter);
        if (!DictList.empty()){
            selectDict.setSelection(getLastDict());
        }
        selectDict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString(PreferenceVars.dictLanguage, (String) selectDict.getSelectedItem());
                editor.commit();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
