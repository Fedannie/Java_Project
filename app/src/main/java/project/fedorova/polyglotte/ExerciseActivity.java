package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.fedorova.polyglotte.layouts.CenterLockHorizontalScrollview;
import project.fedorova.polyglotte.layouts.ExerciseListAdapter;


public class ExerciseActivity extends Activity implements View.OnClickListener{

    private Button wholeBtn;
    private Button prevExerciseBtn;
    private Button nextExerciseBtn;
    private CenterLockHorizontalScrollview exerciseScrollView;
    private int currIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
    }

    @Override
    public void onClick(View v) {}
}
