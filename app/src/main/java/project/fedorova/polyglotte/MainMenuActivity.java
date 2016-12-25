package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
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

import project.fedorova.polyglotte.data.db.DBConnector;
import project.fedorova.polyglotte.data.DictList;
import project.fedorova.polyglotte.data.PhraseList;
import project.fedorova.polyglotte.data.ReadWriteManager;

public class MainMenuActivity extends Activity implements View.OnClickListener {
    private Spinner selectDict;
    private String dictLanguage;
    private String nativeLanguage;
    private boolean wasChangedNativeLanguage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        init();

        loadDictList();
        loadSettings();
    }

    @Override
    protected void onStart() {
        super.onPause();
        setSelectDict();
    }

    @Override
    protected void onRestart() {
        super.onPause();
        setSelectDict();
    }

    @Override
    protected void onDestroy() {
        super.onPause();
        setSelectDict();
    }

    @Override
    protected void onResume() {
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
                intentE.putExtra(getString(R.string.dict_lang), dictLanguage);
                intentE.putExtra(getString(R.string.native_lang), nativeLanguage);
                startActivity(intentE);
                break;
            case (R.id.preferencesButton):
                Intent intentP = new Intent(this, PrefActivity.class);
                startActivityForResult(intentP, 1);
                break;
            case (R.id.dictionaryButton):
                Intent intentD = new Intent(this, DictActivity.class);
                intentD.putExtra(getString(R.string.dict_lang), dictLanguage);
                intentD.putExtra(getString(R.string.native_lang), nativeLanguage);
                startActivity(intentD);
                break;
            case (R.id.phrasebookButton):
                PhraseList phraseList = PhraseList.getInstance(this, dictLanguage, nativeLanguage, wasChangedNativeLanguage);
                if (phraseList.isERROR()) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getString(R.string.no_internet_head))
                            .setMessage(getString(R.string.no_internet_body))
                            .setNeutralButton(getString(R.string.ok), null)
                            .show();
                } else {
                    Intent intentPh = new Intent(this, PhraseActivity.class);
                    intentPh.putExtra(getString(R.string.dict_lang), dictLanguage);
                    intentPh.putExtra(getString(R.string.native_lang), nativeLanguage);
                    intentPh.putExtra(getString(R.string.native_lang_changed), wasChangedNativeLanguage);
                    startActivity(intentPh);
                }
                break;
            case (R.id.translatorButton):
                Intent intentTr = new Intent(this, TranslatorActivity.class);
                intentTr.putExtra(getString(R.string.dict_lang), dictLanguage);
                intentTr.putExtra(getString(R.string.native_lang), nativeLanguage);
                startActivity(intentTr);
                break;
            case (R.id.clearPreferences):
                DBConnector dbConnector = new DBConnector(this, dictLanguage, nativeLanguage);
                dbConnector.deleteAll();
                SharedPreferences sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.clear();
                editor.commit();
                break;
            case (R.id.deleteDict):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete word?")
                        .setMessage("Are you sure you want to delete this dictionary?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            try {
                                int item = selectDict.getSelectedItemPosition();
                                dictList.deleteDict((String) selectDict.getSelectedItem());
                                if (item > 0) {
                                    selectDict.setSelection(item - 1);
                                } else if (!dictList.empty()) {
                                    selectDict.setSelection(item);
                                }
                                dictLanguage = (String) selectDict.getSelectedItem();
                                loadDict();
                            } catch (Exception e) {
                                Toast.makeText(MainMenuActivity.this, getString(R.string.error_delete_dict), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                break;
            case (R.id.addDictButton):
                Intent intentAD = new Intent(this, PopUpAddDict.class);
                startActivityForResult(intentAD, 2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                nativeLanguage = data.getStringExtra(getString(R.string.language));
                wasChangedNativeLanguage = true;
            }
            if (requestCode == 2) {
                dictLanguage = data.getStringExtra(getString(R.string.dictionary));
                loadDictList();
                setSelectDict();
            }
        }
    }

    private int getLastDict() throws NoSuchElementException{
        DictList dictList = DictList.getInstance();
        String[] dictionaries = dictList.getDictList().toArray(new String[]{});
        for (int i = 0; i < dictionaries.length; i++) {
            if (dictionaries[i].equals(dictLanguage)) {
                return i;
            }
        }
        return 0;
    }

    private void loadDict() {
        DictList dictList = DictList.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dictList.getDictList().toArray(new String[]{}));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDict.setAdapter(adapter);
        setSelectDict();
        selectDict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                safeDict();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setSelectDict() {
        DictList dictList = DictList.getInstance();
        if (!dictList.empty()){
            selectDict.setSelection(getLastDict());
        }
    }

    private void safeDict() {
        dictLanguage = (String) selectDict.getSelectedItem();
    }

    private void loadSettings() {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        if (getString(R.string.yes).equals(sPref.getString(getString(R.string.first_time), getString(R.string.yes)))) {
            SharedPreferences.Editor editor = sPref.edit();
            Intent intentNL = new Intent(this, PopUpSelectNativeLanguage.class);
            startActivityForResult(intentNL, 1);
            editor.putString(getString(R.string.first_time), getString(R.string.no));
            editor.commit();
        } else {
            nativeLanguage = sPref.getString(getString(R.string.native_lang), getString(R.string.default_language));
            dictLanguage = sPref.getString(getString(R.string.dict_lang), getString(R.string.default_language));
        }
        loadDict();
    }

    private void loadDictList() {
        ReadWriteManager rwManager = ReadWriteManager.getInstance();
        Set<String> dicts = rwManager.convertStringToSet(rwManager.readFromFile(this, getString(R.string.dict_list)));
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
        readWriteManager.writeToFile(this, getString(R.string.dict_list), readWriteManager.convertSetToString(dictList.getDictList()));
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(getString(R.string.dict_lang), dictLanguage);
        editor.putString(getString(R.string.native_lang), nativeLanguage);
        editor.commit();
    }

    private void init() {
        Button btnExercise;
        Button btnPref;
        Button btnDict;
        Button btnPhrase;
        Button btnTrans;
        Button addDictBtn;
        Button deleteDictBtn;
        Button clearPrefBtn;

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

        clearPrefBtn = (Button) findViewById(R.id.clearPreferences);
        clearPrefBtn.setOnClickListener(this);

        selectDict = (Spinner) findViewById(R.id.dictSpinner);
        selectDict.setPrompt(getString(R.string.your_dictionaries));
    }
}
