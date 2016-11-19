package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PhraseActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasebook);
    }
    @Override
    public void onClick(View v) {

    }
}
