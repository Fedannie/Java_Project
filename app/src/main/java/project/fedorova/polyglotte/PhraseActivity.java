package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.fedorova.polyglotte.data.Phrase;
import project.fedorova.polyglotte.data.PhraseList;
import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.layouts.ExerciseListAdapter;
import project.fedorova.polyglotte.translator.language.Language;
import project.fedorova.polyglotte.translator.translate.Translate;

public class PhraseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasebook);

        Intent intent = getIntent();
        TextView dictionary = (TextView) findViewById(R.id.dictPhrasebook);
        dictionary.setText(intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));

        ExpandableListView phrasesList = (ExpandableListView) findViewById(R.id.phrasesELV);
        PhraseListAdapterHelper adapterHelper = new PhraseListAdapterHelper();
        phrasesList.setAdapter(adapterHelper.getAdapter(intent.getStringExtra(PreferenceVars.DICT_LANGUAGE),
                intent.getStringExtra(PreferenceVars.NATIVE_LANGUAGE),
                intent.getBooleanExtra(PreferenceVars.NATIVE_LANG_CHANGED, true)));
        phrasesList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Intent intentWordWatcher = new Intent(PhraseActivity.this, PopUpShowPhrase.class);
            intentWordWatcher.putExtra(PreferenceVars.PHRASE, adapterHelper.getChildPhrase(groupPosition, childPosition));
            intentWordWatcher.putExtra(PreferenceVars.TRANS, adapterHelper.getChildTrans(groupPosition, childPosition));
            startActivity(intentWordWatcher);
            return false;
        });
    }

    private class PhraseListAdapterHelper{
        private static final String TRANSLATION = "translation";
        private static final String THEME = "theme";
        private static final String PHRASE = "phrase";
        private PhraseList phraseList;
        private ArrayList<String> themes = phraseList.getThemes();
        private ArrayList<ArrayList<String>> phrases = phraseList.getPhrases();
        private ArrayList<ArrayList<String>> translations = phraseList.getTranslations();
        private SimpleExpandableListAdapter adapter;
        SimpleExpandableListAdapter getAdapter(String dictLang, String nativeLang, boolean wasChanged) {
            phraseList = PhraseList.getInstance(dictLang, nativeLang, wasChanged);
            ArrayList<Map<String, String>> themeData = new ArrayList<>();
            Map<String, String> m;
            for (String group : themes) {
                m = new HashMap<>();
                m.put(THEME, group);
                themeData.add(m);
            }

            String groupFrom[] = new String[]{THEME};
            int groupTo[] = new int[]{android.R.id.text1};

            ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();

            for (int i = 0; i < phrases.size(); i++) {
                ArrayList<Map<String, String>> childDataItem = new ArrayList<>();
                for (int j = 0; j < phrases.get(i).size(); j++) {
                    m = new HashMap<>();
                    m.put(PHRASE, phrases.get(i).get(j));
                    m.put(TRANSLATION, translations.get(i).get(j));
                    childDataItem.add(m);
                }
                childData.add(childDataItem);
            }

            String childFrom[] = new String[]{PHRASE, TRANSLATION};
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
            return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(PHRASE);
        }

        String getChildTrans(int groupPos, int childPos) {
            return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(TRANSLATION);
        }
    }
}
