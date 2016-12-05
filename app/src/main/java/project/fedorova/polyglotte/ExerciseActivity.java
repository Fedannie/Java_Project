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
        wholeBtn = (Button) findViewById(R.id.startExercise);
        wholeBtn.setOnClickListener(this);
        prevExerciseBtn = (Button) findViewById(R.id.prev);
        wholeBtn.setOnClickListener(this);
        nextExerciseBtn = (Button) findViewById(R.id.next);
        wholeBtn.setOnClickListener(this);
        exerciseScrollView = (CenterLockHorizontalScrollview) findViewById(R.id.scrollView);
        ExerciseListAdapter exerciseLA = new ExerciseListAdapter(this);
        exerciseScrollView.setAdapter(this, exerciseLA);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.prev) {
            if (currIndex != 0) {
                currIndex--;
                exerciseScrollView.setCenter(currIndex);
            }
        } else if (v.getId() == R.id.next) {

            if (currIndex < ExerciseListAdapter.SIZE) {
                exerciseScrollView.setCenter(currIndex);
                currIndex++;
            }
        }
    }
}
