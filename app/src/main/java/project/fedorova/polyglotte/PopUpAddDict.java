package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
        lvMain.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (DictList.addDict(langs[(int) id])) {
                    Toast.makeText(PopUpAddDict.this, langs[(int) id] + " dictionary was created.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PopUpAddDict.this, langs[(int) id] + " dictionary does already exist.", Toast.LENGTH_LONG).show();
                }
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
