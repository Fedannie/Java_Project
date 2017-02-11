package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.db.DBConnector;
import project.fedorova.polyglotte.exercise.double_cl.Connect;

import static project.fedorova.polyglotte.exercise.double_cl.Connect.BUTTONS_COUNT;

public class ExerciseConnect extends Activity implements View.OnClickListener {
    private Connect training;
    private int emptySpaces = 0;
    private List<Button> variantArray;
    private boolean step = false;
    private int[] notEmptyNames = new int[BUTTONS_COUNT];
    private int first_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_connect);

        init();
        showTraining();
    }

    public void onChoiceClick(View view) {
        int index = getInd((Button) view);
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
                first_pos = -1;
                step = false;
                variantArray.get(first_pos).setVisibility(View.VISIBLE);
            }
        }
    }

    private int getInd(Button button) {
        for (int i = 0; i < variantArray.size(); i++) {
            if (variantArray.get(i).equals(button)) {
                return i;
            }
        }
        return 0;
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
        variantArray.add(0, (Button) findViewById(R.id.button_c1));
        variantArray.add(1, (Button) findViewById(R.id.button_c4));
        variantArray.add(2, (Button) findViewById(R.id.button_c7));
        variantArray.add(3, (Button) findViewById(R.id.button_c10));
        variantArray.add(4, (Button) findViewById(R.id.button_c13));
        variantArray.add(5, (Button) findViewById(R.id.button_c3));
        variantArray.add(6, (Button) findViewById(R.id.button_c6));
        variantArray.add(7, (Button) findViewById(R.id.button_c9));
        variantArray.add(8, (Button) findViewById(R.id.button_c12));
        variantArray.add(9, (Button) findViewById(R.id.button_c15));

        DBConnector wordManager = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        Cursor cursor = wordManager.getAllWords();
        training = new Connect(cursor);
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
