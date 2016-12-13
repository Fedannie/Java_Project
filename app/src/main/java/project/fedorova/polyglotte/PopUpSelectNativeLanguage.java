package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.DictList;
import project.fedorova.polyglotte.data.PreferenceVars;

public class PopUpSelectNativeLanguage extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupselectnativelang);
        ListView lvLangs = (ListView) findViewById(R.id.lvMain);
        Resources res = getResources();
        final String[] langs = res.getStringArray(R.array.wholeLanguageList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, langs);
        lvLangs.setAdapter(adapter);
        lvLangs.setOnItemClickListener((parent, view, position, id) -> {
            DictList dictList = DictList.getInstance();
            Toast.makeText(PopUpSelectNativeLanguage.this, "Your native language is " + langs[(int) id] + "!\n You may change it in preferences.", Toast.LENGTH_LONG).show();
            PreferenceVars prefVar = PreferenceVars.getInstance();
            prefVar.setNativeLang(langs[(int) id]);
            onBackPressed();
        });
    }
}
