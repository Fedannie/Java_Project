package project.fedorova.polyglotte;

import android.app.Activity;
import android.database.Cursor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.PreferenceVars;

public class DictActivity extends Activity implements View.OnClickListener {
    public static final int REQUEST_TO_REFRESH = 1;
    private Button repeatAllBtn;
    private Button filterBtn;
    private DBConnector wordManager;
    private WordListAdapter wordListAdapter;
    private ListView wordList;
    private PreferenceVars prefVars = PreferenceVars.getInstance();
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        init();
        setWordList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setWordList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setWordList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWordList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addWordFAB):
                Intent intentAWA = new Intent(this, PopUpAddNewWord.class);
                startActivityForResult(intentAWA, REQUEST_TO_REFRESH);
                break;
            case (R.id.filterButton):
                Intent intentFT = new Intent(this, PopUpFilterThemes.class);
                startActivity(intentFT);
                break;
            case (R.id.repeatButton):
                Intent intentR = new Intent(this, RepeatWordActivity.class);
                startActivity(intentR);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Your word was added", Toast.LENGTH_SHORT).show();
            refresh();
        }
    }

    private void refresh() {
        setWordList();
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    private void init() {
        FloatingActionButton addWordBtn = (FloatingActionButton) findViewById(R.id.addWordFAB);
        addWordBtn.setOnClickListener(this);

        filterBtn = (Button) findViewById(R.id.filterButton);
        filterBtn.setOnClickListener(this);

        repeatAllBtn = (Button) findViewById(R.id.repeatButton);
        repeatAllBtn.setOnClickListener(this);

        wordManager = new DBConnector(this,
                prefVars.getDictLang(),
                prefVars.getNativeLang());
        cursor = wordManager.getAllWords();

        wordList = (ListView) findViewById(R.id.wordList);
    }

    private static class WordListAdapter extends CursorAdapter {
        public WordListAdapter (Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.wordlistitem, parent, false);
            ViewHolder viewHolder = new ViewHolder((TextView) view.findViewById(R.id.wordTVWatcher), (TextView) view.findViewById(R.id.translationsTV));
            view.setTag(viewHolder);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();

            TextView wordTV = viewHolder.wordTV;
            String word = cursor.getString(DBConnector.NUM_WORD_TITLE);
            wordTV.setText(word);

            TextView translationsTV = viewHolder.translationsTV;
            String translations = cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION);
            if (!cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS).equals("")) {
                translations += ", " + cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS);
            }
            translationsTV.setText(translations);
        }

        private static class ViewHolder {
            public final TextView wordTV;
            public final TextView translationsTV;
            public ViewHolder(TextView word, TextView translations) {
                wordTV = word;
                translationsTV = translations;
            }

        }
    }

    private void setWordList() {
        wordListAdapter = new WordListAdapter(this, cursor, 0);
        wordList.setAdapter(wordListAdapter);
        wordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentWordWatcher = new Intent(this, PopUpWordWatcher.class);
            intentWordWatcher.putExtra(PreferenceVars.WORD_INDEX, position);
            startActivityForResult(intentWordWatcher, REQUEST_TO_REFRESH);
        });
    }
}
