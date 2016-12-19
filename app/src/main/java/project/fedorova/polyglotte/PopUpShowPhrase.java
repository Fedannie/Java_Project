package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PopUpShowPhrase extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_show_phrase);

        Intent intent = getIntent();
        TextView phraseTV = (TextView) findViewById(R.id.phraseTV);
        phraseTV.setText(intent.getStringExtra(getString(R.string.phrase)));
        TextView translationTV = (TextView) findViewById(R.id.translationPhraseTV);
        translationTV.setText(intent.getStringExtra(getString(R.string.translation)));
    }
}
