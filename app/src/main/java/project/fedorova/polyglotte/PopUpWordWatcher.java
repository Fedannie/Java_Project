package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.DictList;
import project.fedorova.polyglotte.data.PreferenceVars;

public class PopUpWordWatcher extends Activity implements View.OnClickListener {
    private DBConnector wordManager;
    private Cursor cursor;
    private TextView wordTV;
    private TextView mainTransTV;
    private TextView extraTransTV;
    private Button backBtn;
    private ImageButton editWordIB;
    private ImageButton deleteWordIB;
    private ImageButton left;
    private ImageButton right;
    private int wordPos;
    private Intent intent;
    private PreferenceVars prefVars = PreferenceVars.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_word_watcher);

        init();
        setTV();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.backBtnWatcher):
                finish();
                break;
            case (R.id.leftBtnWatcher):
                if (wordPos > 0) {
                    wordPos--;
                    setTV();
                }
                break;
            case (R.id.rightBtnWatcher):
                if (wordPos < cursor.getCount() - 1) {
                    wordPos++;
                    setTV();
                }
                break;
            case (R.id.editWordIB):
                Intent intentEditor = new Intent(this, PopUpAddNewWord.class);
                intentEditor.putExtra(PreferenceVars.WORD_INDEX, wordPos);
                intentEditor.putExtra(PreferenceVars.IF_EDIT, PreferenceVars.YES);
                startActivityForResult(intentEditor, DictActivity.REQUEST_TO_REFRESH);
                break;
            case (R.id.deleteWordIB):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete word?")
                        .setMessage("Are you sure you want to delete this word?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            wordManager.delete(cursor.getString(DBConnector.NUM_WORD_ID));
                            Intent intent1 = getIntent();
                            setResult(RESULT_OK, intent1);
                            PopUpWordWatcher.this.finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
                setResult(RESULT_OK, getIntent());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Word was edited", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, getIntent());
        }
    }

    private void setTV() {
        cursor.move(wordPos + 1);

        wordTV.setText(cursor.getString(DBConnector.NUM_WORD_TITLE));

        mainTransTV.setText(cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));

        extraTransTV.setText(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS));
    }

    private void init() {
        intent = getIntent();
        wordPos = intent.getIntExtra(PreferenceVars.WORD_INDEX, 0);

        wordManager = new DBConnector(this,
                prefVars.getDictLang(),
                prefVars.getNativeLang());
        cursor = wordManager.getAllWords();

        wordTV = (TextView) findViewById(R.id.wordTVWatcher);

        mainTransTV = (TextView) findViewById(R.id.mainTranslationTVWatcher);

        extraTransTV = (TextView) findViewById(R.id.extraTranslationsTVWatcher);

        backBtn = (Button) findViewById(R.id.backBtnWatcher);
        backBtn.setOnClickListener(this);

        editWordIB = (ImageButton) findViewById(R.id.editWordIB);
        editWordIB.setOnClickListener(this);

        deleteWordIB = (ImageButton) findViewById(R.id.deleteWordIB);
        deleteWordIB.setOnClickListener(this);

        left = (ImageButton) findViewById(R.id.leftBtnWatcher);
        left.setOnClickListener(this);

        right = (ImageButton) findViewById(R.id.rightBtnWatcher);
        right.setOnClickListener(this);
    }
}
