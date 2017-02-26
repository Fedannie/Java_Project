package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import project.fedorova.polyglotte.data.db.DBConnector;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class PopUpAddNewWord extends Activity implements View.OnClickListener{
    private static final String ONLY_COMMAS = "Sorry, only commas to divide words";
    private static final String ONLY_COMMAS_AP = "Sorry, only apostrophe and commas to divide words";
    private static final String ONLY_LETTERS = "Sorry, only letters and numbers.";
    private DBConnector wordBase;
    private TextInputLayout wordTIL;
    private TextInputLayout mainTranslationTIL;
    private TextInputLayout translationTIL;
    private TextInputLayout examplesTIL;
    private TextView plusExtraTrans;
    private TextView plusExamples;
    private UUID wordID = null;
    private LinearLayout mainLayout;
    private Intent intent;
    private List<String> themeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_word);
        intent = getIntent();
        init();
        if (intent.getStringExtra(getString(R.string.if_edit)).equals(getString(R.string.yes))) {
            setDataToEdit();
        }
        themeList = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addThemeToWord):
                Intent intentT = new Intent(this, PopUpAddTheme.class);
                intentT.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentT.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                startActivityForResult(intentT, 1);
                break;
            case (R.id.saveWord):
                if (!readyToSave()) {
                    Toast.makeText(PopUpAddNewWord.this, getString(R.string.msg_word_not_complete), Toast.LENGTH_SHORT).show();
                    break;
                }
                if (wordID != null) {
                    wordBase.delete(wordID);
                }
                saveNewWord();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case (R.id.backBtn):
                finish();
                break;
            case (R.id.plusExamples):
                plusExamples.setVisibility(View.INVISIBLE);
                examplesTIL.setVisibility(View.VISIBLE);
                break;
            case (R.id.plusExtraTranslations):
                plusExtraTrans.setVisibility(View.INVISIBLE);
                translationTIL.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String addedTheme = data.getStringExtra(getString(R.string.theme));
        if (!addedTheme.equals("") && !themeList.contains(addedTheme)){
            themeList.add(addedTheme);
            addThemeView(addedTheme);
        }
    }

    private void addThemeView(String theme) {
        TextView newTheme = new TextView(this);
        newTheme.setText(theme);
        AppBarLayout.LayoutParams params = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        newTheme.setLayoutParams(params);
        newTheme.setTextSize(15f);
        mainLayout.addView(newTheme);
        newTheme.setOnClickListener(v -> {
            themeList.remove(theme);
            mainLayout.removeAllViews();
            for (String s : themeList) {
                addThemeView(s);
            }
        });
    }

    private void saveNewWord() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        String title = "";
        String mainTrans = "";
        String extraTrans = "";
        String examples = "";
        try {
            title = wordTIL.getEditText().getText().toString();
            mainTrans = mainTranslationTIL.getEditText().getText().toString();
            extraTrans = translationTIL.getEditText().getText().toString();
            examples = examplesTIL.getEditText().getText().toString();
        } catch (Exception e) {
            Toast.makeText(this, "Oops. Something went wrong with saving.", Toast.LENGTH_SHORT).show();
        }
        if (themeList.size() == 0) {
            themeList.add(getString(R.string.no_theme));
        }

        wordBase.insertWord(new Word(UUID.randomUUID(), title,
                mainTrans,
                readWriteManager.convertStringToSet(extraTrans),
                new HashSet<>(themeList),
                readWriteManager.convertStringToSet(examples), 0));
    }

    private void init() {
        wordBase = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));

        Button addThemeBtn = (Button) findViewById(R.id.addThemeToWord);
        addThemeBtn.setOnClickListener(this);

        Button saveWordBtn = (Button) findViewById(R.id.saveWord);
        saveWordBtn.setOnClickListener(this);

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        mainLayout = (LinearLayout) findViewById(R.id.scroll_themes);

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
                if (s != null && containsPunct(s.toString())) {
                    wordTIL.setErrorEnabled(true);
                    wordTIL.setError(ONLY_LETTERS);
                } else {
                    wordTIL.setErrorEnabled(false);
                }
            }
        });
        wordTIL.setErrorEnabled(false);

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
                if (s != null && containsPunct(mainTranslationTIL.getEditText().getText().toString())) {
                    mainTranslationTIL.setErrorEnabled(true);
                    mainTranslationTIL.setError(ONLY_LETTERS);
                } else {  //TODO Translate word and check the translation
                    mainTranslationTIL.setErrorEnabled(false);
                }
            }
        });
        mainTranslationTIL.setErrorEnabled(false);

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
                if (s != null && containsPunctExceptComma(translationTIL.getEditText().getText().toString())) {
                    translationTIL.setErrorEnabled(true);
                    translationTIL.setError(ONLY_COMMAS);
                } else {
                    translationTIL.setErrorEnabled(false);
                }
            }
        });
        translationTIL.setErrorEnabled(false);

        examplesTIL = (TextInputLayout) findViewById(R.id.examplesLIT);
        examplesTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && containsPunctExceptComma(examplesTIL.getEditText().getText().toString().replace("'", ""))) {
                    examplesTIL.setErrorEnabled(true);
                    examplesTIL.setError(ONLY_COMMAS_AP);
                } else {
                    examplesTIL.setErrorEnabled(false);
                }
            }
        });
        examplesTIL.setErrorEnabled(false);

        plusExamples = (TextView) findViewById(R.id.plusExamples);
        plusExamples.setOnClickListener(this);

        plusExtraTrans = (TextView) findViewById(R.id.plusExtraTranslations);
        plusExtraTrans.setOnClickListener(this);
    }

    private void setDataToEdit() {
        Word word = wordBase.getWord(wordID = UUID.fromString(intent.getStringExtra(getString(R.string.word_id))));
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        if (!readWriteManager.convertSetToString(word.getTranslations()).equals("")) {
            onClick(plusExtraTrans);
        }
        if (!readWriteManager.convertSetToString(word.getExamples()).equals("")) {
            onClick(plusExamples);
        }
        try {
            wordTIL.getEditText().setText(word.getWord());
            mainTranslationTIL.getEditText().setText(word.getMainTranslation());
            translationTIL.getEditText().setText(readWriteManager.convertSetToString(word.getTranslations()));
            examplesTIL.getEditText().setText(readWriteManager.convertSetToString(word.getExamples()));
            for (String theme : word.getThemes()) {
                addThemeView(theme);
            }
        } catch (Exception e) {}
    }

    private boolean readyToSave() {
        try {
            return wordTIL.getEditText().getText()!= null &&
                    mainTranslationTIL.getEditText().getText() != null &&
                    !wordTIL.getEditText().getText().toString().equals("") &&
                    !mainTranslationTIL.getEditText().getText().toString().equals("") &&
                    !wordTIL.isErrorEnabled() &&
                    !mainTranslationTIL.isErrorEnabled() &&
                    !translationTIL.isErrorEnabled() &&
                    !examplesTIL.isErrorEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean containsPunctExceptComma (String text) {
        String check = text.replace(",", "").replace(" ", "");
        return !check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }

    private boolean containsPunct(String text) {
        String check = text.replace(" ", "");
        return !check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }
}