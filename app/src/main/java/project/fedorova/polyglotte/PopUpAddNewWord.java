package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class PopUpAddNewWord extends Activity implements View.OnClickListener{
    private Button addThemeBtn;
    private EditText wordET;
    private EditText mainTranslationET;
    private EditText extraTranslationsET;
    private Button saveWordBtn;
    private DBConnector wordBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_word);

        wordBase = new DBConnector(this);

        addThemeBtn = (Button) findViewById(R.id.addThemeToWord);
        addThemeBtn.setOnClickListener(this);

        saveWordBtn = (Button) findViewById(R.id.saveWord);
        saveWordBtn.setOnClickListener(this);

        wordET = (EditText) findViewById(R.id.wordET);
        wordET.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        mainTranslationET = (EditText) findViewById(R.id.translationET);
        mainTranslationET.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        extraTranslationsET = (EditText) findViewById(R.id.extratranslationsET);
        extraTranslationsET.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addThemeToWord):
                break;
            case (R.id.saveWord):
                ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
                wordBase.insertWord(new Word(UUID.randomUUID(), wordET.getText().toString(),
                        mainTranslationET.getText().toString(),
                        readWriteManager.convertStringToSet(extraTranslationsET.getText().toString()),
                        null));
                super.onDestroy();
                break;
            default:
                break;
        }
    }
}
