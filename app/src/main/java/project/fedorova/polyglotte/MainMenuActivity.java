package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnExercise;
        Button btnPref;
        Button btnDict;
        Button btnPhrase;
        Button btnTrans;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnExercise = (Button) findViewById(R.id.exerciseButton);
        btnExercise.setOnClickListener(this);
        btnPref = (Button) findViewById(R.id.preferencesButton);
        btnPref.setOnClickListener(this);
        btnDict = (Button) findViewById(R.id.dictionaryButton);
        btnDict.setOnClickListener(this);
        btnPhrase = (Button) findViewById(R.id.phrasebookButton);
        btnPhrase.setOnClickListener(this);
        btnTrans = (Button) findViewById(R.id.translatorButton);
        btnTrans.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.exerciseButton):
                Intent intentE = new Intent(this, ExerciseActivity.class);
                startActivity(intentE);
                break;
            case (R.id.preferencesButton):
                Intent intentP = new Intent(this, PrefActivity.class);
                startActivity(intentP);
                break;
            case (R.id.dictionaryButton):
                Intent intentD = new Intent(this, DictActivity.class);
                startActivity(intentD);
                break;
            case (R.id.phrasebookButton):
                Intent intentPh = new Intent(this, PhraseActivity.class);
                startActivity(intentPh);
                break;
            case (R.id.translatorButton):
                Intent intentTr = new Intent(this, TranslatorActivity.class);
                startActivity(intentTr);
                break;
            default:
                break;
        }
    }
}
