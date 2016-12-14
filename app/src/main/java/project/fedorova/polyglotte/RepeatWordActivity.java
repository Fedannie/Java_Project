package project.fedorova.polyglotte;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class RepeatWordActivity extends Activity implements View.OnClickListener {
    private ImageButton left;
    private ImageButton right;
    private Button stop;
    private TextView wordTV;
    private TextView mainTrans;
    private TextView extraTrans;
    private ArrayList<Word> order;
    private PreferenceVars prefVars = PreferenceVars.getInstance();

    public RepeatWordActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_word);

        init();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.leftBtnRepeat):
                //TODO next word
                break;
            case (R.id.rightBtnRepeat):
                //TODO previous word
                break;
            case (R.id.stopBtn):
                finish();
                break;
            default:
                //TODO show translation
                break;
        }
    }

    private void init() {
        left = (ImageButton) findViewById(R.id.leftBtnRepeat);
        left.setOnClickListener(this);

        right = (ImageButton) findViewById(R.id.rightBtnRepeat);
        right.setOnClickListener(this);

        stop = (Button) findViewById(R.id.stopBtn);
        stop.setOnClickListener(this);

        wordTV = (TextView) findViewById(R.id.wordTVRepeat);

        mainTrans = (TextView) findViewById(R.id.mainTranslationTVRepeat);

        extraTrans = (TextView) findViewById(R.id.extraTranslationsTVRepeat);

        order = new ArrayList<>();

        DBConnector database = new DBConnector(this,
                prefVars.getDictLang(),
                prefVars.getNativeLang());
        Cursor cursor = database.getAllWords();
        cursor.moveToFirst();

        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();

        while (cursor.moveToNext()) {
            order.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES))));
        }
        Collections.shuffle(order);
    }
}
