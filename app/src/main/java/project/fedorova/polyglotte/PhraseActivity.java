package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.fedorova.polyglotte.data.PhraseList;
import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.translator.language.Language;
import project.fedorova.polyglotte.translator.translate.Translate;

public class PhraseActivity extends Activity {
    private static final String TRANSLATION = "translation";
    private static final String THEME = "theme";
    private static final String PHRASE = "phrase";
    private PhraseList phraseList = PhraseList.getInstance();
    private TextView dictionary;
    private PreferenceVars prefVars = PreferenceVars.getInstance();
    private Translate translate = Translate.getInstance();
    // названия компаний (групп)
    ArrayList<String> themes = phraseList.getThemes();

    // названия телефонов (элементов)
    ArrayList<ArrayList<String>> phrases = phraseList.getPhrases();
    // коллекция для групп
    ArrayList<Map<String, String>> themeData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список атрибутов группы или элемента
    Map<String, String> m;
    ExpandableListView phrasesList;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasebook);

        dictionary = (TextView) findViewById(R.id.dictPhrasebook);
        dictionary.setText(prefVars.getDictLang());
        // заполняем коллекцию групп из массива с названиями групп
        themeData = new ArrayList<Map<String, String>>();
        for (String group : themes) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put(THEME, group); // имя компании
            themeData.add(m);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[] {THEME};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        for (int i = 0; i < phrases.size(); i++) {

            // создаем коллекцию элементов для первой группы
            childDataItem = new ArrayList<Map<String, String>>();
            // заполняем список атрибутов для каждого элемента
            for (String phrase : phrases.get(i)) {
                m = new HashMap<String, String>();
                m.put(PHRASE, phrase); // название телефона
                try {
                    m.put(TRANSLATION, translate.execute(phrase,
                                    Language.fromString(translate.getLanguageCode(prefVars.getDictLang())),
                                    Language.fromString(translate.getLanguageCode(prefVars.getNativeLang()))));
                } catch (Exception e) {
                    Toast.makeText(this, translate.getLanguageCode(prefVars.getDictLang()) + " " + translate.getLanguageCode(prefVars.getNativeLang()) + "\n" + phrase, Toast.LENGTH_SHORT).show();
                    m.put(TRANSLATION, "");
                }
                childDataItem.add(m);
            }
            // добавляем в коллекцию коллекций
            childData.add(childDataItem);
        }

        // список атрибутов элементов для чтения
        String childFrom[] = new String[] {PHRASE, TRANSLATION};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[] {R.id.stringTVList, R.id.translationTVList};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                themeData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                R.layout.wordlistitem,
                childFrom,
                childTo);

        phrasesList = (ExpandableListView) findViewById(R.id.phrasesELV);
        phrasesList.setAdapter(adapter);
    }
}
