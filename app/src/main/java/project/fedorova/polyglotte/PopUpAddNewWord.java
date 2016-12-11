package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PopUpAddNewWord extends Activity implements View.OnClickListener{
    private Button addThemeBtn;
    private EditText wordInput;
    private EditText mainTranslationInput;
    private EditText extraTranslationsInput;
    private Button saveWordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_word);

        addThemeBtn = (Button) findViewById(R.id.addThemeToWord);
        addThemeBtn.setOnClickListener(this);

        saveWordBtn = (Button) findViewById(R.id.saveWord);
        saveWordBtn.setOnClickListener(this);

        wordInput = (EditText) findViewById(R.id.wordinput);

        mainTranslationInput = (EditText) findViewById(R.id.translationinput);

        extraTranslationsInput = (EditText) findViewById(R.id.extratranslationsinput);
    }

    @Override
    public void onClick(View v) {

    }

}
