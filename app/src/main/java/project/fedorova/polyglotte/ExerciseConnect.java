package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.exercise.double_cl.Connect;

import static project.fedorova.polyglotte.exercise.Training.LIVES_CNT;
import static project.fedorova.polyglotte.exercise.double_cl.Connect.BUTTONS_COUNT;

public class ExerciseConnect extends Activity implements View.OnClickListener {
    private Connect training;
    private int emptySpaces = 0;
    private List<Button> variantArray;
    private boolean step = false;
    private int[] notEmptyNames = new int[BUTTONS_COUNT];
    private int first_pos;
    private static final int[] BUTTON_IDS = {
            R.id.button_c1,
            R.id.button_c4,
            R.id.button_c7,
            R.id.button_c10,
            R.id.button_c13,
            R.id.button_c3,
            R.id.button_c6,
            R.id.button_c9,
            R.id.button_c12,
            R.id.button_c15
    };
    private List<ImageView> livesArray;
    private int lives = LIVES_CNT;
    private static final int[] LIVES_IDS = {
            R.id.live_con1,
            R.id.live_con2,
            R.id.live_con3,
            R.id.live_con4,
            R.id.live_con5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_connect);

        init();
        showTraining();
    }

    public void onChoiceClick(View view) {
        int index = variantArray.indexOf(view);
        if (index < 5) {
            training.setWord(notEmptyNames[index]);
        } else {
            training.setTranslation(((Button) view).getText().toString());
        }
        if (!step) {
            first_pos = index;
            step = true;
        } else {
            if (training.check()) {
                emptySpaces--;
                training.correctAnswer();
                view.setVisibility(View.INVISIBLE);
                variantArray.get(first_pos).setVisibility(View.INVISIBLE);
                first_pos = -1;
                step = false;
                if (emptySpaces == 0) {
                    training.movePosition(training.getChoicesCnt());
                    showTraining();
                }
            } else {
                //TODO animation
                training.incorrectAnswer();
                first_pos = -1;
                step = false;
                variantArray.get(first_pos).setVisibility(View.VISIBLE);
                lives--;
                if (lives == 0) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getString(R.string.oops))
                            .setMessage(getString(R.string.failed_exercise))
                            .setPositiveButton(getString(R.string.ok), (dialog, which) -> ExerciseConnect.this.finish())
                            .show();
                } else {
                    livesArray.get(lives).setVisibility(View.INVISIBLE); //TODO animation
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.quit):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.quit_training_head))
                        .setMessage(getString(R.string.quit_training_body))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseConnect.this.finish())
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                break;
            default:
                break;
        }
    }

    private void init() {
        Intent intent = getIntent();

        Button quit = (Button) findViewById(R.id.quit_con);
        quit.setOnClickListener(this);

        variantArray = new ArrayList<>(BUTTONS_COUNT);
        for (int i = 0; i < BUTTONS_COUNT; i++) {
            variantArray.add(i, (Button) findViewById(BUTTON_IDS[i]));
        }

        livesArray = new ArrayList<>(LIVES_CNT);
        for (int i = 0; i < LIVES_CNT; i++) {
            livesArray.add(i, (ImageView) findViewById(LIVES_IDS[i]));
        }

        training = new Connect(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
    }

    private void showTraining() {
        try {
            if (!training.getTraining()) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.oops))
                        .setMessage(getString(R.string.msg_not_enough_words))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseConnect.this.finish())
                        .show();
            } else {
                List<String> choicesVars = training.getChoicesVars();
                for (int i = 0; i < BUTTONS_COUNT; i++) {
                    if (choicesVars.get(i) == null) {
                        variantArray.get(i).setVisibility(View.INVISIBLE);
                        variantArray.get(i).setEnabled(false);
                    } else {
                        if (i < 5) {
                            notEmptyNames[i] = emptySpaces;
                        }
                        variantArray.get(i).setVisibility(View.VISIBLE);
                        variantArray.get(i).setEnabled(true);
                        variantArray.get(i).setText(choicesVars.get(i));
                        emptySpaces++;
                    }
                }
                emptySpaces /= 2;
            }
        } catch (Exception e) {
            //TODO animation finish
            ExerciseConnect.this.finish();
        }
    }
}
