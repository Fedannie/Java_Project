package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import project.fedorova.polyglotte.data.DictList;

public class PopUpAddDict extends Activity implements View.OnClickListener{
    ListView lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddictpopup);
        lvMain = (ListView) findViewById(R.id.lvMain);
        Resources res = getResources();
        final String[] langs = res.getStringArray(R.array.wholeLanguageList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, langs);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener((parent, view, position, id) -> {
            DictList dictList = DictList.getInstance();
            if (dictList.addDict(langs[(int) id])) {
                Toast.makeText(PopUpAddDict.this, langs[(int) id] + getString(R.string.msg_dict_created), Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                intent.putExtra(getString(R.string.dictionary), langs[(int) id]);
                setResult(RESULT_OK, intent);
            } else {
                Toast.makeText(PopUpAddDict.this, langs[(int) id] + getString(R.string.msg_dict_exist), Toast.LENGTH_LONG).show();
            }
            onBackPressed();
        });
    }

    @Override
    public void onClick(View v) {

    }
}
