package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.db.DBConnector;

public class PopUpAddTheme extends Activity implements View.OnClickListener{
    private List<String> themes;
    private AutoCompleteTextView themeACTV;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_theme);

        Intent intent = getIntent();
        DBConnector dbConnector = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        Cursor cursor = dbConnector.getAllThemes();
        cursor.moveToFirst();
        themes = new ArrayList<>();
        if (cursor.getCount() > 0) {
            themes.add(cursor.getString(DBConnector.NUM_THEME_TITLE));
        }
        while (cursor.moveToNext()) {
            if (!themes.contains(cursor.getString(DBConnector.NUM_THEME_TITLE))) {
                themes.add(cursor.getString(DBConnector.NUM_THEME_TITLE));
            }
        }

        themeACTV = (AutoCompleteTextView) findViewById(R.id.themeACTV);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, themes);
        themeACTV.setAdapter(adapter);
        Button accept = (Button) findViewById(R.id.accept);
        accept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.accept):
                String newAdd = themeACTV.getText() == null ? "" : themeACTV.getText().toString();
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
