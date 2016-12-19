package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
            Toast.makeText(PopUpSelectNativeLanguage.this, getString(R.string.msg_native_lang_is) + langs[(int) id] + getString(R.string.msg_change_nat_lang), Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            intent.putExtra(getString(R.string.language), langs[(int) id]);
            setResult(RESULT_OK, intent);
            onBackPressed();
        });
    }
}
