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
    private DBConnector wordManager;
    private ListView wordList;
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
        Intent intent = getIntent();
        switch (view.getId()) {
            case (R.id.addWordFAB):
                Intent intentAWA = new Intent(this, PopUpAddNewWord.class);
                intentAWA.putExtra(PreferenceVars.DICT_LANGUAGE, intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
                intentAWA.putExtra(PreferenceVars.NATIVE_LANGUAGE, intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE));
                startActivityForResult(intentAWA, REQUEST_TO_REFRESH);
                break;
            case (R.id.filterButton):
                Intent intentFT = new Intent(this, PopUpFilterThemes.class);
                intentFT.putExtra(PreferenceVars.DICT_LANGUAGE, intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
                intentFT.putExtra(PreferenceVars.NATIVE_LANGUAGE, intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE));
                startActivity(intentFT);
                break;
            case (R.id.repeatButton):
                if (wordManager.getAllWords().getCount() == 0) {
                    Toast.makeText(this, "Sorry, you have not got any words yet", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intentR = new Intent(this, RepeatWordActivity.class);
                intentR.putExtra(PreferenceVars.DICT_LANGUAGE, intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
                intentR.putExtra(PreferenceVars.NATIVE_LANGUAGE, intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE));
                startActivity(intentR);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
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
        Intent intent = getIntent();
        FloatingActionButton addWordBtn = (FloatingActionButton) findViewById(R.id.addWordFAB);
        addWordBtn.setOnClickListener(this);

        Button filterBtn = (Button) findViewById(R.id.filterButton);
        filterBtn.setOnClickListener(this);

        Button repeatAllBtn = (Button) findViewById(R.id.repeatButton);
        repeatAllBtn.setOnClickListener(this);

        wordManager = new DBConnector(this,
                intent.getStringExtra(PreferenceVars.DICT_LANGUAGE),
                intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE));
        cursor = wordManager.getAllWords();

        wordList = (ListView) findViewById(R.id.wordList);

        TextView dictionary = (TextView) findViewById(R.id.dictDictionary);
        dictionary.setText(intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
    }

    private static class WordListAdapter extends CursorAdapter {
        WordListAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.wordlistitem, parent, false);
            ViewHolder viewHolder = new ViewHolder((TextView) view.findViewById(R.id.stringTVList), (TextView) view.findViewById(R.id.translationTVList));
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
            final TextView wordTV;
            final TextView translationsTV;
            ViewHolder(TextView word, TextView translations) {
                wordTV = word;
                translationsTV = translations;
            }

        }
    }

    private void setWordList() {
        WordListAdapter wordListAdapter = new WordListAdapter(this, cursor, 0);
        wordList.setAdapter(wordListAdapter);
        wordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentWordWatcher = new Intent(this, PopUpWordWatcher.class);
            Intent intent = getIntent();
            intentWordWatcher.putExtra(PreferenceVars.WORD_INDEX, position);
            intentWordWatcher.putExtra(PreferenceVars.DICT_LANGUAGE, intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
            intentWordWatcher.putExtra(PreferenceVars.NATIVE_LANGUAGE, intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE));
            startActivityForResult(intentWordWatcher, REQUEST_TO_REFRESH);
        });
    }
}
