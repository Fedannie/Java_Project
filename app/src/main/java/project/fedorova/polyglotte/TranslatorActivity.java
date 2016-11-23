package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.fedorova.polyglotte.translator.language.Language;
import project.fedorova.polyglotte.translator.translate.Translate;

public class TranslatorActivity extends Activity implements View.OnClickListener {
    EditText fromET;
    TextView transTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator);
        fromET = (EditText) findViewById(R.id.textToTrans);
        transTV = (TextView) findViewById(R.id.transResult);
        fromET.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String translation = Translate.execute(fromET.getText().toString(), Language.fromString("en"), Language.fromString("es"));
                    transTV.setText(translation);
                } catch (Exception e) {
                    Toast.makeText(TranslatorActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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
    public void onClick(View v) {

    }
}
