package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.fedorova.polyglotte.data.PhraseList;

public class PhraseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasebook);

        Intent intent = getIntent();
        TextView dictionary = (TextView) findViewById(R.id.dictPhrasebook);
        String dict;
        dictionary.setText(dict = intent.getStringExtra(getString(R.string.dict_lang)));

        ExpandableListView phrasesList = (ExpandableListView) findViewById(R.id.phrasesELV);
        PhraseListAdapterHelper adapterHelper = new PhraseListAdapterHelper();
        phrasesList.setAdapter(adapterHelper.getAdapter(dict,
                intent.getStringExtra(getString(R.string.native_lang)),
                intent.getBooleanExtra(getString(R.string.native_lang_changed), true)));
        phrasesList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Intent intentWordWatcher = new Intent(PhraseActivity.this, PopUpShowPhrase.class);
            intentWordWatcher.putExtra(getString(R.string.phrase), adapterHelper.getChildPhrase(groupPosition, childPosition));
            intentWordWatcher.putExtra(getString(R.string.translation), adapterHelper.getChildTrans(groupPosition, childPosition));
            startActivity(intentWordWatcher);
            return false;
        });
    }

    private class PhraseListAdapterHelper{
        //private static final String TRANSLATION = "translation";
        //private static final String THEME = "theme";
        //private static final String PHRASE = "phrase";
        private PhraseList phraseList;
        private ArrayList<String> themes;
        private ArrayList<ArrayList<String>> phrases;
        private ArrayList<ArrayList<String>> translations;
        private SimpleExpandableListAdapter adapter;
        SimpleExpandableListAdapter getAdapter(String dictLang, String nativeLang, boolean wasChanged) {
            phraseList = PhraseList.getInstance(PhraseActivity.this, dictLang, nativeLang, wasChanged);
            themes = phraseList.getThemes();
            phrases = phraseList.getPhrases();
            translations = phraseList.getTranslations();
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

            for (int i = 0; i < phrases.size(); i++) {
                ArrayList<Map<String, String>> childDataItem = new ArrayList<>();
                for (int j = 0; j < phrases.get(i).size(); j++) {
                    m = new HashMap<>();
                    m.put(getString(R.string.phrase), phrases.get(i).get(j));
                    m.put(getString(R.string.translation), translations.get(i).get(j));
                    childDataItem.add(m);
                }
                childData.add(childDataItem);
            }

            String childFrom[] = new String[]{getString(R.string.phrase), getString(R.string.translation)};
            int childTo[] = new int[]{R.id.stringTVList, R.id.translationTVList};

            adapter = new SimpleExpandableListAdapter(
                    PhraseActivity.this,
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
    }
}
