package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;

public class PopUpWordWatcher extends Activity implements View.OnClickListener {
    private DBConnector wordManager;
    private Cursor cursor;
    private TextView wordTV;
    private TextView mainTransTV;
    private TextView extraTransTV;
    private TextView examplesTV;
    private int wordPos;

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
                intentEditor.putExtra(getString(R.string.word_index), wordPos);
                intentEditor.putExtra(getString(R.string.if_edit), getString(R.string.yes));
                startActivityForResult(intentEditor, DictActivity.REQUEST_TO_REFRESH);
                break;
            case (R.id.deleteWordIB):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.delete_word_head))
                        .setMessage(getString(R.string.delete_word_body))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            wordManager.delete(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)));
                            Intent intent1 = getIntent();
                            setResult(RESULT_OK, intent1);
                            PopUpWordWatcher.this.finish();
                        })
                        .setNegativeButton(getString(R.string.no), null)
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
            Toast.makeText(this, getString(R.string.word_edited), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, getIntent());
        }
    }

    private void setTV() {
        cursor.move(wordPos + 1);

        wordTV.setText(cursor.getString(DBConnector.NUM_WORD_TITLE));

        mainTransTV.setText(cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));

        extraTransTV.setText(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS));

        examplesTV.setText(cursor.getString(DBConnector.NUM_WORD_EXAMPLES).replace(",","\n"));
    }

    private void init() {
        Intent intent = getIntent();

        wordPos = intent.getIntExtra(getString(R.string.word_index), 0);
        wordManager = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        cursor = wordManager.getAllWords();

        wordTV = (TextView) findViewById(R.id.wordTVWatcher);

        mainTransTV = (TextView) findViewById(R.id.mainTranslationTVWatcher);

        extraTransTV = (TextView) findViewById(R.id.extraTranslationsTVWatcher);

        examplesTV = (TextView) findViewById(R.id.examplesTVWatcher);

        Button backBtn = (Button) findViewById(R.id.backBtnWatcher);
        backBtn.setOnClickListener(this);

        ImageButton editWordIB = (ImageButton) findViewById(R.id.editWordIB);
        editWordIB.setOnClickListener(this);

        ImageButton deleteWordIB = (ImageButton) findViewById(R.id.deleteWordIB);
        deleteWordIB.setOnClickListener(this);

        ImageButton left = (ImageButton) findViewById(R.id.leftBtnWatcher);
        left.setOnClickListener(this);

        ImageButton right = (ImageButton) findViewById(R.id.rightBtnWatcher);
        right.setOnClickListener(this);
    }
}
