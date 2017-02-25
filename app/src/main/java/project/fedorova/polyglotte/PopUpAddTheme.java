package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PopUpAddTheme extends Activity implements View.OnClickListener{
    private List<String> themes;
    private AutoCompleteTextView themeACTV;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_theme);
        themeACTV = (AutoCompleteTextView) findViewById(R.id.themeACTV);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, themes);
        themeACTV.setAdapter(adapter);
        Button accept = (Button) findViewById(R.id.accept);
        accept.setOnClickListener(this);
        themes = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.accept):
                String newAdd = themeACTV.getText() == null ? "" : themeACTV.getText().toString();

                if (!themes.contains(newAdd)) {
                    themes.add(newAdd);

                    adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_dropdown_item_1line, themes);
                    themeACTV.setAdapter(adapter);
                } else {
                    newAdd = "";
                }

                Intent intentRes = new Intent();
                intentRes.putExtra(getString(R.string.theme), newAdd);
                setResult(RESULT_OK, intentRes);
                finish();
                break;
            default:
                break;
        }
    }
}
