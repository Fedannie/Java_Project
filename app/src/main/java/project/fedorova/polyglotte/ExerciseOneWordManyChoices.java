package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.db.DBConnector;
import project.fedorova.polyglotte.exercise.DoubleWordTraining;
import project.fedorova.polyglotte.exercise.double_cl.ChooseTransByWord;

import static project.fedorova.polyglotte.exercise.DoubleWordTraining.BUTTONS_COUNT;

public class ExerciseOneWordManyChoices extends Activity implements View.OnClickListener {
    private DoubleWordTraining training;
    TextView entry;
    private List<Button> variantArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_double);

        init();
        showTraining();
    }

    public void onChoiceClick(View view) {
        if (training.check(((Button) view).getText().toString())) {
            //TODO animation
            showTraining();
        }  //TODO else animation
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.quit):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.quit_training_head))
                        .setMessage(getString(R.string.quit_training_body))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseOneWordManyChoices.this.finish())
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                break;
            default:
                break;
        }
    }

    private void init(){
        Intent intent = getIntent();
        String mode = intent.getStringExtra(getString(R.string.mode));

        entry = (TextView) findViewById(R.id.entry);
        Button quit = (Button) findViewById(R.id.quit_d);
        quit.setOnClickListener(this);


        variantArray = new ArrayList<>(BUTTONS_COUNT);
        variantArray.add(0, (Button) findViewById(R.id.button16));
        variantArray.add(1, (Button) findViewById(R.id.button17));
        variantArray.add(2, (Button) findViewById(R.id.button18));
        variantArray.add(3, (Button) findViewById(R.id.button19));
        variantArray.add(4, (Button) findViewById(R.id.button20));
        variantArray.add(5, (Button) findViewById(R.id.button21));
        variantArray.add(6, (Button) findViewById(R.id.button22));
        variantArray.add(7, (Button) findViewById(R.id.button23));
        variantArray.add(8, (Button) findViewById(R.id.button24));


        DBConnector wordManager = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        Cursor cursor = wordManager.getAllWords();

        if (mode.equals(getString(R.string.trans_by_word))) {
            training = new ChooseTransByWord(cursor);
        }
    }

    private void showTraining() {
        try {
            if (!training.getTraining()) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.oops))
                        .setMessage(getString(R.string.msg_not_enough_words))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseOneWordManyChoices.this.finish())
                        .show();
            } else {
                entry.setText(training.getEntry());
                List<String> choicesVars = training.getChoicesVars();
                for (int i = 0; i < BUTTONS_COUNT; i++) {
                    if (choicesVars.get(i) == null) {
                        variantArray.get(i).setVisibility(View.INVISIBLE);
                        variantArray.get(i).setEnabled(false);
                    } else {
                        variantArray.get(i).setVisibility(View.VISIBLE);
                        variantArray.get(i).setEnabled(true);
                        variantArray.get(i).setText(choicesVars.get(i));
                    }
                }
            }
        } catch (Exception e) {
            //TODO animation finish
            ExerciseOneWordManyChoices.this.finish();
        }
    }
}
