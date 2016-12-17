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
    PreferenceVars prefVars = PreferenceVars.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasebook);

        TextView dictionary = (TextView) findViewById(R.id.dictPhrasebook);
        dictionary.setText(prefVars.getDictLang());

        ExpandableListView phrasesList = (ExpandableListView) findViewById(R.id.phrasesELV);
        PhraseListAdapterHelper adapterHelper = new PhraseListAdapterHelper();
        phrasesList.setAdapter(adapterHelper.getAdapter());
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
        private PhraseList phraseList = PhraseList.getInstance();
        private PreferenceVars prefVars = PreferenceVars.getInstance();
        private Translate translate = Translate.getInstance();
        private ArrayList<String> themes = phraseList.getThemes();
        private ArrayList<ArrayList<String>> phrases = phraseList.getPhrases();
        private SimpleExpandableListAdapter adapter;
        SimpleExpandableListAdapter getAdapter() {
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
                for (String phrase : phrases.get(i)) {
                    m = new HashMap<>();
                    try{
                        m.put(PHRASE, translate.execute(phrase,
                            Language.fromString(translate.getLanguageCode(PreferenceVars.DEFAULT_LANG)),
                            Language.fromString(translate.getLanguageCode(prefVars.getDictLang()))));
                        m.put(TRANSLATION, translate.execute(phrase,
                                Language.fromString(translate.getLanguageCode(PreferenceVars.DEFAULT_LANG)),
                                Language.fromString(translate.getLanguageCode(prefVars.getNativeLang()))));
                    } catch (Exception e) {
                        Toast.makeText(PhraseActivity.this, "Unknown rror with translation", Toast.LENGTH_SHORT).show();
                        m.put(TRANSLATION, "");
                    }
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
