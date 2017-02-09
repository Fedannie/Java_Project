package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import project.fedorova.polyglotte.data.language.Language;
import project.fedorova.polyglotte.translator.translate.Translate;

public class TranslatorActivity extends Activity implements View.OnClickListener {
    private EditText fromET;
    private TextView transTV;
    private Spinner langFrom;
    private Spinner langTo;
    private ProgressBar translatePB;
    private Translate translate = Translate.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator);
        Intent intent = getIntent();

        translatePB = (ProgressBar) findViewById(R.id.translateProgressBar);
        fromET = (EditText) findViewById(R.id.textToTrans);
        fromET.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                callTranslate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        transTV = (TextView) findViewById(R.id.transResult);

        langFrom = (Spinner) findViewById(R.id.langFrom);
        langFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                callTranslate();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        langTo = (Spinner) findViewById(R.id.langTo);
        langTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                callTranslate();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        try {
            langFrom.setSelection(Arrays.asList(getResources().getStringArray(R.array.wholeLanguageList)).indexOf(intent.getStringExtra(getString(R.string.dict_lang))));
        } catch (Exception e) {
            langFrom.setSelection(Arrays.asList(getResources().getStringArray(R.array.wholeLanguageList)).indexOf(intent.getStringExtra(getString(R.string.default_language))));
        }
        try {
            langTo.setSelection(Arrays.asList(getResources().getStringArray(R.array.wholeLanguageList)).indexOf(intent.getStringExtra(getString(R.string.native_lang))));
        } catch (Exception e) {
            langFrom.setSelection(Arrays.asList(getResources().getStringArray(R.array.wholeLanguageList)).indexOf(intent.getStringExtra(getString(R.string.default_language))));
        }

    }

    @Override
    public void onClick(View v) {
    }

    public void callTranslate() {
        translatePB.setVisibility(View.VISIBLE);
        try {
            String translation = translate.execute(fromET.getText().toString(), getLang(langFrom), getLang(langTo));
            transTV.setText(translation);
        } catch (Exception e) {
            Toast.makeText(TranslatorActivity.this, getString(R.string.error) + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            translatePB.setVisibility(View.INVISIBLE);
        }
    }

    private Language getLang(Spinner sLang) {
        try {
            String pLang = translate.getLanguageCode(sLang.getSelectedItem().toString());
            return Language.fromString(pLang);
        } catch (Exception e) {
            return translate.DEFAULT_LANG;
        }
    }
}
