package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExerciseActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        TextView dictionary = (TextView) findViewById(R.id.dictExercise);
        dictionary.setText(intent.getStringExtra(getString(R.string.dict_lang)));
    }

    @Override
    public void onClick(View v) {}
}
