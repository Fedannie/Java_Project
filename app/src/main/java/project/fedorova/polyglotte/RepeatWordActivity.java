package project.fedorova.polyglotte;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
    private RelativeLayout background;
    private TextView wordTV;
    private TextView mainTrans;
    private TextView extraTrans;
    private TextView examplesTV;
    private ArrayList<Word> order;
    private PreferenceVars prefVars = PreferenceVars.getInstance();
    private int currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_word);

        init();
        showWord();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.leftBtnRepeat):
                if (currentWord > 0){
                    currentWord--;
                    showWord();
                }
                break;
            case (R.id.rightBtnRepeat):
                if (currentWord < order.size() - 1) {
                    currentWord++;
                    showWord();
                }
                break;
            case (R.id.stopBtn):
                finish();
                break;
            default:
                showTrans();
                break;
        }
    }

    private void showWord() {
        wordTV.setText(order.get(currentWord).getWord());
        mainTrans.setText("");
        extraTrans.setText("");
        examplesTV.setText("");
    }

    private void showTrans() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        mainTrans.setText(order.get(currentWord).getMainTranslation());
        extraTrans.setText(readWriteManager.convertSetToString(order.get(currentWord).getTranslations()));
        examplesTV.setText(readWriteManager.convertSetToString(order.get(currentWord).getExamples()).replace(",", "\n"));
    }

    private void init() {
        background = (RelativeLayout) findViewById(R.id.relativeRepeat);
        background.setOnClickListener(this);

        left = (ImageButton) findViewById(R.id.leftBtnRepeat);
        left.setOnClickListener(this);

        right = (ImageButton) findViewById(R.id.rightBtnRepeat);
        right.setOnClickListener(this);

        stop = (Button) findViewById(R.id.stopBtn);
        stop.setOnClickListener(this);

        wordTV = (TextView) findViewById(R.id.wordTVRepeat);
        wordTV.setOnClickListener(this);

        mainTrans = (TextView) findViewById(R.id.mainTranslationTVRepeat);
        mainTrans.setOnClickListener(this);

        extraTrans = (TextView) findViewById(R.id.extraTranslationsTVRepeat);
        extraTrans.setOnClickListener(this);

        examplesTV = (TextView) findViewById(R.id.examplesTVRepeat);
        examplesTV.setOnClickListener(this);

        order = new ArrayList<>();

        DBConnector database = new DBConnector(this,
                prefVars.getDictLang(),
                prefVars.getNativeLang());
        Cursor cursor = database.getAllWords();

        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();

        while (cursor.moveToNext()) {
            order.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_EXAMPLES))));
        }
        Collections.shuffle(order);
        currentWord = 0;
    }
}
