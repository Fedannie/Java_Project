package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.layouts.CenterLockHorizontalScrollview;
import project.fedorova.polyglotte.layouts.ExerciseListAdapter;


public class ExerciseActivity extends Activity implements View.OnClickListener{
    private TextView dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        dictionary = (TextView) findViewById(R.id.dictExercise);
        dictionary.setText(intent.getStringExtra(PreferenceVars.DICT_LANGUAGE));
    }

    @Override
    public void onClick(View v) {}
}
