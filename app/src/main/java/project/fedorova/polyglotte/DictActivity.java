package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Loader;
import android.database.Cursor;
import android.os.StrictMode;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.Word;

public class DictActivity extends Activity implements View.OnClickListener {
    private static final int LOADER_ID = 0;
    private Button repeatAllBtn;
    private Button filterBtn;
    private DBConnector wordManager;
    private WordListAdapter wordListAdapter;
    private ListView wordList;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);

        FloatingActionButton addWordBtn = (FloatingActionButton) findViewById(R.id.addWordFAB);
        addWordBtn.setOnClickListener(this);
        filterBtn = (Button) findViewById(R.id.filterButton);
        filterBtn.setOnClickListener(this);

        repeatAllBtn = (Button) findViewById(R.id.repeatButton);
        repeatAllBtn.setOnClickListener(this);

        wordManager = new DBConnector(this);
        cursor = wordManager.getAllWords();

        wordList = (ListView) findViewById(R.id.wordList);
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
                startActivity(intentAWA);
                break;
            case (R.id.filterButton):
                showPopup();
                break;
            case (R.id.repeatButton):
                wordManager.deleteAll();
                break;
            default:
                break;


        }
    }

    private PopupWindow pw;
    private void showPopup() {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupfilterthemes, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                false);
        popupWindow.showAsDropDown(filterBtn, 50, -30);
    }

    private static class WordListAdapter extends CursorAdapter {
        public WordListAdapter (Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.wordlistitem, parent, false);
            ViewHolder viewHolder = new ViewHolder((TextView) view.findViewById(R.id.wordTV), (TextView) view.findViewById(R.id.translationsTV));
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
            String translations = cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION) + ", " + cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS);
            translationsTV.setText(translations);
        }

        private static class ViewHolder {
            public final TextView wordTV;
            public final TextView translationsTV;

            public ViewHolder(TextView word, TextView translations) {
                wordTV = word;
                translationsTV= translations;
            }

        }
    }

    private void setWordList() {
        wordListAdapter = new WordListAdapter(this, cursor, 0);
        wordList.setAdapter(wordListAdapter);
        wordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentWordWatcher = new Intent(this, PopUpAddNewWord.class);
            startActivity(intentWordWatcher);
        });
    }
}
