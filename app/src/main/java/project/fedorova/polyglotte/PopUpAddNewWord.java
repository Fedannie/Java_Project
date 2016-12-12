package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class PopUpAddNewWord extends Activity implements View.OnClickListener{
    private static final String ONLY_COMMAS = "Sorry, only commas to divide words";
    private static final String ONLY_LETTERS = "Sorry, only letters and numbers.";
    private Button addThemeBtn;
    private Button saveWordBtn;
    private DBConnector wordBase;
    private TextInputLayout wordTIL;
    private TextInputLayout mainTranslationTIL;
    private TextInputLayout translationTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_word);

        wordBase = new DBConnector(this);

        addThemeBtn = (Button) findViewById(R.id.addThemeToWord);
        addThemeBtn.setOnClickListener(this);

        saveWordBtn = (Button) findViewById(R.id.saveWord);
        saveWordBtn.setOnClickListener(this);

        wordTIL = (TextInputLayout) findViewById(R.id.wordinput);
        wordTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (containsPunct(s.toString())) {
                    wordTIL.setErrorEnabled(true);
                    wordTIL.setError(ONLY_LETTERS);
                } else {
                    wordTIL.setErrorEnabled(false);
                }
            }
        });

        mainTranslationTIL = (TextInputLayout) findViewById(R.id.translationinput);
        mainTranslationTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (containsPunct(mainTranslationTIL.getEditText().getText().toString())) {
                    mainTranslationTIL.setErrorEnabled(true);
                    mainTranslationTIL.setError(ONLY_LETTERS);
                } else {  //TODO Translate word and check the translation
                    mainTranslationTIL.setErrorEnabled(false);
                }
            }
        });

        translationTIL = (TextInputLayout) findViewById(R.id.extratranslationsinput);
        translationTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (containsPunctExceptComma(translationTIL.getEditText().getText().toString())) {
                    translationTIL.setErrorEnabled(true);
                    translationTIL.setError(ONLY_COMMAS);
                } else {
                    translationTIL.setErrorEnabled(false);
                }
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
                wordBase.insertWord(new Word(UUID.randomUUID(), wordTIL.getEditText().getText().toString(),
                        mainTranslationTIL.getEditText().getText().toString(),
                        readWriteManager.convertStringToSet(translationTIL.getEditText().getText().toString()),
                        null));
                finish();
                break;
            default:
                break;
        }
    }

    private boolean containsPunctExceptComma (String text) {
        String check = text.replace(",", "").replace(" ", "");
        return check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }

    private boolean containsPunct(String text) {
        String check = text.replace(" ", "");
        return check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }
}