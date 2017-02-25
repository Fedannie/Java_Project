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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themesactivity);

        Intent intent = getIntent();
        TextView dictionary = (TextView) findViewById(R.id.dictThemes);
        dictionary.setText(intent.getStringExtra(getString(R.string.dict_lang)));
        ExpandableListView themesList = (ExpandableListView) findViewById(R.id.themesELV);

        dbConnector = new DBConnector(ThemesActivity.this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        cursor = dbConnector.getAllThemes();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
                  getInf();
        }
        while (cursor.moveToNext()) {
            getInf();
        }

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
    }

    private void getInf() {
        String lastTheme = cursor.getString(DBConnector.NUM_THEME_TITLE);
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
/*
        ThemesActivity.ThemeListAdapterHelper adapterHelper = new ThemesActivity.ThemeListAdapterHelper();
        themesList.setAdapter(adapterHelper.getAdapter(
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang))));
        themesList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Intent intentWordWatcher = new Intent(ThemesActivity.this, PopUpShowPhrase.class);
            intentWordWatcher.putExtra(getString(R.string.phrase), adapterHelper.getChildPhrase(groupPosition, childPosition));
            intentWordWatcher.putExtra(getString(R.string.translation), adapterHelper.getChildTrans(groupPosition, childPosition));
            startActivity(intentWordWatcher);
            return false;
        });
    }

    private class ThemeListAdapterHelper{
        private SimpleExpandableListAdapter adapter;
        SimpleExpandableListAdapter getAdapter(String dictLang, String nativeLang) {

            ArrayList<Map<String, String>> themeData = new ArrayList<>();
            Map<String, String> m;
            for (String group : themes) {
                m = new HashMap<>();
                m.put(getString(R.string.theme), group);
                themeData.add(m);
            }

            String groupFrom[] = new String[]{getString(R.string.theme)};
            int groupTo[] = new int[]{android.R.id.text1};

            ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();

            for (int i = 0; i < words.size(); i++) {
                ArrayList<Map<String, String>> childDataItem = new ArrayList<>();
                for (int j = 0; j < words.get(i).size(); j++) {
                    m = new HashMap<>();
                    m.put(getString(R.string.phrase), words.get(i).get(j));
                    m.put(getString(R.string.translation), translations.get(i).get(j));
                    childDataItem.add(m);
                }
                childData.add(childDataItem);
            }

            String childFrom[] = new String[]{getString(R.string.phrase), getString(R.string.translation)};
            int childTo[] = new int[]{R.id.stringTVList, R.id.translationTVList};

            adapter = new SimpleExpandableListAdapter(
                    ThemesActivity.this,
                    themeData,
                    android.R.layout.simple_expandable_list_item_1,
                    groupFrom,
                    groupTo,
                    childData,
                    R.layout.wordlistitem,
                    childFrom,
                    childTo);
            return adapter;
        }

        String getChildPhrase(int groupPos, int childPos) {
            return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(getString(R.string.phrase));
        }

        String getChildTrans(int groupPos, int childPos) {
            return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(getString(R.string.translation));
        }
    }*/
}
