package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.db.DBConnector;

import com.github.lzyzsd.circleprogress.DonutProgress;

public class DictActivity extends Activity implements View.OnClickListener {
    public static final int REQUEST_TO_REFRESH = 1;
    private DBConnector wordManager;
    private ListView wordList;
    Cursor cursor;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.dictionary);
        chooseWordList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        init();
        chooseWordList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        chooseWordList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        chooseWordList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chooseWordList();
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()) {
            case (R.id.addWordFAB):
                Intent intentAWA = new Intent(this, PopUpAddNewWord.class);
                intentAWA.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentAWA.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentAWA.putExtra(getString(R.string.if_edit), getString(R.string.no));
                startActivityForResult(intentAWA, REQUEST_TO_REFRESH);
                break;
            case (R.id.repeatButton):
                if (wordManager.getAllWords().getCount() == 0) {
                    Toast.makeText(this, getString(R.string.msg_no_words), Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intentR = new Intent(this, RepeatWordActivity.class);
                intentR.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentR.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
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
        if(getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().heightPixels) {
            setWordListLand();
        } else {
            setWordListPort();
        }
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    private void init() {
        Intent intent = getIntent();
        FloatingActionButton addWordBtn = (FloatingActionButton) findViewById(R.id.addWordFAB);
        addWordBtn.setOnClickListener(this);

        Button repeatAllBtn = (Button) findViewById(R.id.repeatButton);
        repeatAllBtn.setOnClickListener(this);

        wordManager = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        cursor = wordManager.getAllWords();

        wordList = (ListView) findViewById(R.id.wordList);

        TextView dictionary = (TextView) findViewById(R.id.dictDictionary);
        dictionary.setText(intent.getStringExtra(getString(R.string.dict_lang)));
    }

    private static class WordListAdapter extends CursorAdapter {
        @LayoutRes int res;
        WordListAdapter(Context context, Cursor cursor, int flags, @LayoutRes int resource) {
            super(context, cursor, flags);
            res = resource;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(res, parent, false);
            if (res == R.layout.wordlistitem) {
                ViewHolderPort viewHolder = new ViewHolderPort((TextView) view.findViewById(R.id.stringTVList), (TextView) view.findViewById(R.id.translationTVList), (DonutProgress) view.findViewById(R.id.progress));
                view.setTag(viewHolder);
            } else {
                ViewHolderLand viewHolder = new ViewHolderLand((TextView) view.findViewById(R.id.stringTVListLand), (TextView) view.findViewById(R.id.mainTransTVListLand), (TextView) view.findViewById(R.id.extraTransTVListLand), (DonutProgress) view.findViewById(R.id.progressLand));
                view.setTag(viewHolder);
            }
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            if (res == R.layout.wordlistitem) {
                ViewHolderPort viewHolder = (ViewHolderPort) view.getTag();

                TextView wordTV = viewHolder.wordTV;
                String word = cursor.getString(DBConnector.NUM_WORD_TITLE);
                wordTV.setText(word);

                TextView translationsTV = viewHolder.translationsTV;
                String translations = cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION);
                if (!cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS).equals("")) {
                    translations += ", " + cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS);
                }
                translationsTV.setText(translations);

                DonutProgress donutProgress = viewHolder.donutProgress;
                donutProgress.setProgress(cursor.getInt(DBConnector.NUM_WORD_KNOWLEDGE));
            } else {
                ViewHolderLand viewHolder = (ViewHolderLand) view.getTag();

                TextView wordTV = viewHolder.wordTV;
                String word = cursor.getString(DBConnector.NUM_WORD_TITLE);
                wordTV.setText(word);

                TextView translationsTV = viewHolder.mainTransTV;
                String mainTranslation = cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION);
                translationsTV.setText(mainTranslation);

                TextView extraTransTV = viewHolder.mainTransTV;
                String translations = "";
                if (!cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS).equals("")) {
                    translations += ", " + cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS);
                }
                extraTransTV.setText(translations);

                DonutProgress donutProgress = viewHolder.donutProgress;
                donutProgress.setProgress(cursor.getInt(DBConnector.NUM_WORD_KNOWLEDGE));
            }
        }

        private static class ViewHolderPort {
            final TextView wordTV;
            final TextView translationsTV;
            final DonutProgress donutProgress;
            ViewHolderPort(TextView word, TextView translations, DonutProgress donut) {
                wordTV = word;
                translationsTV = translations;
                donutProgress = donut;
            }
        }

        private static class ViewHolderLand {
            final TextView wordTV;
            final TextView mainTransTV;
            final TextView extraTransTV;
            final DonutProgress donutProgress;
            ViewHolderLand(TextView word, TextView mainTrans, TextView extraTrans, DonutProgress donut) {
                wordTV = word;
                mainTransTV = mainTrans;
                extraTransTV = extraTrans;
                donutProgress = donut;
            }
        }
    }

    private void setWordListPort() {
        WordListAdapter wordListAdapter = new WordListAdapter(this, cursor, 0, R.layout.wordlistitem);
        setWordList(wordListAdapter);
    }


    private void setWordListLand() {
        WordListAdapter wordListAdapter = new WordListAdapter(this, cursor, 0, R.layout.wordlistitemland);
        setWordList(wordListAdapter);
    }

    private void setWordList(WordListAdapter wordListAdapter) {
        wordList.setAdapter(wordListAdapter);
        wordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentWordWatcher = new Intent(this, PopUpWordWatcher.class);
            Intent intent = getIntent();
            intentWordWatcher.putExtra(getString(R.string.word_index), position);
            intentWordWatcher.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
            intentWordWatcher.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
            startActivityForResult(intentWordWatcher, REQUEST_TO_REFRESH);
        });
    }

    private void chooseWordList() {
        if(getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().heightPixels) {
            setWordListLand();
        } else {
            setWordListPort();
        }
    }
}
