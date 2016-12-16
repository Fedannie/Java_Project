package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.layouts.CenterLockHorizontalScrollview;
import project.fedorova.polyglotte.layouts.ExerciseListAdapter;


public class ExerciseActivity extends Activity implements View.OnClickListener{
    private TextView dictionary;
    private Button wholeBtn;
    private Button prevExerciseBtn;
    private Button nextExerciseBtn;
    private CenterLockHorizontalScrollview exerciseScrollView;
    private int currIndex = 0;
    PreferenceVars prefVars = PreferenceVars.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        dictionary = (TextView) findViewById(R.id.dictExercise);
        dictionary.setText(prefVars.getDictLang());
    }

    @Override
    public void onClick(View v) {}
}
