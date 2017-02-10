package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExerciseActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        init();

        TextView dictionary = (TextView) findViewById(R.id.dictExercise);
        dictionary.setText(intent.getStringExtra(getString(R.string.dict_lang)));
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()) {
            case (R.id.transByWord):
                Intent intentTBW = new Intent(this, ExerciseUniqueWord.class);
                intentTBW.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentTBW.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentTBW.putExtra(getString(R.string.mode), getString(R.string.trans_by_word));
                startActivity(intentTBW);
                break;
            case (R.id.wordByTrans):
                Intent intentWBT = new Intent(this, ExerciseUniqueWord.class);
                intentWBT.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentWBT.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentWBT.putExtra(getString(R.string.mode), getString(R.string.word_by_trans));
                startActivity(intentWBT);
                break;
            default:
                break;
        }
    }

    private void init() {
        Button transByWord = (Button) findViewById(R.id.transByWord);
        transByWord.setOnClickListener(this);

        Button wordByTrans = (Button) findViewById(R.id.wordByTrans);
        wordByTrans.setOnClickListener(this);

        Button chooseTransByWord = (Button) findViewById(R.id.chooseTransByWord);
        chooseTransByWord.setOnClickListener(this);

        Button chooseWordByTrans = (Button) findViewById(R.id.chooseWordByTrans);
        chooseWordByTrans.setOnClickListener(this);


        Button wholeExercise = (Button) findViewById(R.id.wholeExercise);
        wholeExercise.setOnClickListener(this);

        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(this);

        Button correctMistakes = (Button) findViewById(R.id.correctTheMistake);
        correctMistakes.setOnClickListener(this);
    }
}
