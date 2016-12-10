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
import android.widget.Toast;

import java.util.NoSuchElementException;
import java.util.Set;

import project.fedorova.polyglotte.data.DictList;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.PreferenceVars;

public class MainMenuActivity extends Activity implements View.OnClickListener {
    SharedPreferences sPref;
    Button btnExercise;
    Button btnPref;
    Button btnDict;
    Button btnPhrase;
    Button btnTrans;
    Button addDictBtn;
    Button deleteDictBtn;
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

        deleteDictBtn = (Button) findViewById(R.id.deleteDict);
        deleteDictBtn.setOnClickListener(this);

        selectDict = (Spinner) findViewById(R.id.dictSpinner);
        selectDict.setPrompt("Your dictionaries");

        loadSettings();
        loadDictList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
    }

    @Override
    protected void onStop() {
        super.onStop();
        safeDict();
    }

    @Override
    protected void onPause() {
        super.onPause();
        safeDict();
    }

    protected void onDestroy() {
        super.onPause();
        safeDict();
        safeSettings();
    }

    @Override
    public void onClick(View view) {
        DictList dictList = DictList.getInstance();
        safeDict();
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
            case (R.id.deleteDict):
                try {
                    int item = selectDict.getSelectedItemPosition();
                    dictList.deleteDict((String) selectDict.getSelectedItem());
                    if (item > 0) {
                        selectDict.setSelection(item - 1);
                    } else if (!dictList.empty()) {
                        selectDict.setSelection(item);
                    }
                    loadSettings();
                } catch (Exception e) {
                    Toast.makeText(this, "Error with deleting this dictionary", Toast.LENGTH_SHORT).show();
                }

                break;
            case (R.id.addDictButton):
                Intent intentAD = new Intent(this, PopUpAddDict.class);
                startActivity(intentAD);
                break;
            default:
                break;
        }
    }

    private int getLastDict() throws NoSuchElementException{
        DictList dictList = DictList.getInstance();
        sPref = getPreferences(MODE_PRIVATE);
        String language = sPref.getString(PreferenceVars.DICT_LANGUAGE, "");
        String[] dictionaries = dictList.getDictList().toArray(new String[]{});
        for (int i = 0; i < dictionaries.length; i++) {
            if (dictionaries[i].equals(language)) {
                return i;
            }
        }
        return 0;
    }

    private void loadDict() {
        DictList dictList = DictList.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dictList.getDictList().toArray(new String[]{}));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDict.setAdapter(adapter);
        if (!dictList.empty()){
            selectDict.setSelection(getLastDict());
        }
        selectDict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                safeDict();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void safeDict() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(PreferenceVars.DICT_LANGUAGE, (String) selectDict.getSelectedItem());
        editor.apply();
    }

    private void loadSettings() {
        sPref = getPreferences(MODE_PRIVATE);
        PreferenceVars prefVars = PreferenceVars.getInstance();
        prefVars.setNativeLang(sPref.getString(PreferenceVars.NATIVE_LANGUAGE, PreferenceVars.DEFAULT_LANG));

        loadDict();
    }

    private void loadDictList() {
        ReadWriteManager rwManager = ReadWriteManager.getInstance();
        Set<String> dicts = rwManager.convertStringToSet(rwManager.readFromFile(this, ReadWriteManager.DICT_LIST));
        DictList dictList = DictList.getInstance();
        if (dicts != null) {
            for (String s : dicts) {
                dictList.addDict(s);
            }
        }
    }

    private void safeSettings() {
        DictList dictList = DictList.getInstance();
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        readWriteManager.writeToFile(this, ReadWriteManager.DICT_LIST, readWriteManager.convertSetToString(dictList.getDictList()));
    }
}
