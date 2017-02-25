package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import project.fedorova.polyglotte.data.db.DBConnector;

public class ThemesActivity extends Activity {
    private ArrayList<String> themes = new ArrayList<>();
    private ArrayList<ArrayList<String>> words = new ArrayList<>();
    private ArrayList<ArrayList<String>> translations = new ArrayList<>();
    private ArrayList<ArrayList<UUID>> ids = new ArrayList<>();
    private DBConnector dbConnector;
    private Cursor cursor;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themesactivity);

        intent = getIntent();
        TextView dictionary = (TextView) findViewById(R.id.dictThemes);
        dictionary.setText(intent.getStringExtra(getString(R.string.dict_lang)));
        ExpandableListView themesList = (ExpandableListView) findViewById(R.id.themesELV);

        dbConnector = new DBConnector(ThemesActivity.this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        cursor = dbConnector.getAllThemes();
        cursor.moveToFirst();
        getAllWords();

        List<Map<String, String>> groupData = new ArrayList<>();
        List<List<Map<String, String>>> childData = new ArrayList<>();

        for (int i = 0; i < themes.size(); i++) {
            Map<String, String> curGroupMap = new HashMap<>();
            curGroupMap.put(getString(R.string.theme), themes.get(i));
            groupData.add(curGroupMap);

            List<Map<String, String>> children = new ArrayList<>();
            for (int j = 0; j < words.get(i).size(); j++) {
                Map<String, String> curChildMap = new HashMap<>();
                curChildMap.put(getString(R.string.theme), words.get(i).get(j));
                curChildMap.put(getString(R.string.main_translation), translations.get(i).get(j));
                children.add(curChildMap);
            }
            childData.add(children);
        }

        ExpandableListAdapter mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { getString(R.string.theme) },
                new int[] { android.R.id.text1 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { getString(R.string.theme), getString(R.string.main_translation) },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        themesList.setAdapter(mAdapter);
        themesList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentWordWatcher = new Intent(this, PopUpWordWatcher.class);
            intentWordWatcher.putExtra(getString(R.string.word_index), position);
            intentWordWatcher.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
            intentWordWatcher.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
            startActivityForResult(intentWordWatcher, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
            startActivity(intent);
        }
    }

    private void getAllWords(){
        if (cursor.getCount() > 0) {
            getInf();
        }
        while (cursor.moveToNext()) {
            getInf();
        }
    }

    private void getInf() {
        String lastTheme = cursor.getString(DBConnector.NUM_THEME_TITLE);
        if (themes.contains(lastTheme)) {
            return;
        }
        themes.add(lastTheme);
        Cursor cursorWords = dbConnector.getWordsByTheme(lastTheme);
        ArrayList<String> wordsOfThisTheme = new ArrayList<>();
        ArrayList<String> transWordsOfThisTheme = new ArrayList<>();
        ArrayList<UUID> idsWordsOfThisTheme = new ArrayList<>();
        cursorWords.moveToFirst();
        if (cursorWords.getCount() > 0) {
            wordsOfThisTheme.add(cursorWords.getString(DBConnector.NUM_WORD_TITLE));
            transWordsOfThisTheme.add(cursorWords.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));
            idsWordsOfThisTheme.add(UUID.fromString(cursorWords.getString(DBConnector.NUM_WORD_ID)));
        }
        while (cursorWords.moveToNext()) {
            wordsOfThisTheme.add(cursorWords.getString(DBConnector.NUM_WORD_TITLE));
            transWordsOfThisTheme.add(cursorWords.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));
            idsWordsOfThisTheme.add(UUID.fromString(cursorWords.getString(DBConnector.NUM_WORD_ID)));
        }
        words.add(wordsOfThisTheme);
        translations.add(transWordsOfThisTheme);
        ids.add(idsWordsOfThisTheme);
    }
}
