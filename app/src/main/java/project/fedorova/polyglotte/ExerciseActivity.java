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
                intentTBW.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentTBW.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentTBW.putExtra(getString(R.string.mode), getString(R.string.trans_by_word));
                startActivity(intentTBW);
                break;
            case (R.id.wordByTrans):
                Intent intentWBT = new Intent(this, ExerciseUniqueWord.class);
                intentWBT.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentWBT.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentWBT.putExtra(getString(R.string.mode), getString(R.string.word_by_trans));
                startActivity(intentWBT);
                break;
            case (R.id.correctTheMistake):
                Intent intentCM = new Intent(this, ExerciseUniqueWord.class);
                intentCM.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentCM.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentCM.putExtra(getString(R.string.mode), getString(R.string.correct_mistake));
                startActivity(intentCM);
                break;
            case (R.id.chooseTransByWord):
                Intent intentCHTBW = new Intent(this, ExerciseOneWordManyChoices.class);
                intentCHTBW.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentCHTBW.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentCHTBW.putExtra(getString(R.string.mode), getString(R.string.trans_by_word));
                startActivity(intentCHTBW);
                break;
            case (R.id.chooseWordByTrans):
                Intent intentCHWBT = new Intent(this, ExerciseOneWordManyChoices.class);
                intentCHWBT.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentCHWBT.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                intentCHWBT.putExtra(getString(R.string.mode), getString(R.string.word_by_trans));
                startActivity(intentCHWBT);
                break;
            case (R.id.connect):
                Intent intentC = new Intent(this, ExerciseConnect.class);
                intentC.putExtra(getString(R.string.native_lang), intent.getStringExtra(getString(R.string.native_lang)));
                intentC.putExtra(getString(R.string.dict_lang), intent.getStringExtra(getString(R.string.dict_lang)));
                startActivity(intentC);
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

        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(this);

        Button correctMistakes = (Button) findViewById(R.id.correctTheMistake);
        correctMistakes.setOnClickListener(this);
    }
}
