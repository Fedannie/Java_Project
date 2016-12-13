package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.PreferenceVars;

public class PopUpWordWatcher extends Activity implements View.OnClickListener {
    private DBConnector wordManager;
    private Cursor cursor;
    private TextView wordTV;
    private TextView mainTransTV;
    private TextView extraTransTV;
    private Button backBtn;
    private ImageButton editWordIB;
    private int wordPos;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_word_watcher);

        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.backBtnWatcher):
                finish();
                break;
            case (R.id.editWordIB):
                Intent intentEditor = new Intent(this, PopUpAddNewWord.class);
                intentEditor.putExtra(PreferenceVars.WORD_INDEX, wordPos);
                intentEditor.putExtra(PreferenceVars.IF_EDIT, PreferenceVars.YES);
                startActivity(intentEditor);
                break;
            default:
                break;
        }
    }

    private void init() {
        intent = getIntent();
        wordPos = intent.getIntExtra(PreferenceVars.WORD_INDEX, 0);
        wordManager = new DBConnector(this);

        cursor = wordManager.getAllWords();
        cursor.move(wordPos + 1);

        wordTV = (TextView) findViewById(R.id.wordTVWatcher);
        wordTV.setText(cursor.getString(DBConnector.NUM_WORD_TITLE));

        mainTransTV = (TextView) findViewById(R.id.mainTranslationTVWatcher);
        mainTransTV.setText(cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));

        extraTransTV = (TextView) findViewById(R.id.extraTranslationsTVWatcher);
        extraTransTV.setText(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS));

        backBtn = (Button) findViewById(R.id.backBtnWatcher);
        backBtn.setOnClickListener(this);

        editWordIB = (ImageButton) findViewById(R.id.editWordIB);
        editWordIB.setOnClickListener(this);
    }
}
