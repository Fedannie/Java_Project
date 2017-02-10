package project.fedorova.polyglotte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.db.DBConnector;
import project.fedorova.polyglotte.exercise.UniqueWordTraining;
import project.fedorova.polyglotte.exercise.exersice_classes.CorrectMistakes;
import project.fedorova.polyglotte.exercise.exersice_classes.TransByWord;
import project.fedorova.polyglotte.exercise.exersice_classes.WordByTrans;

public class ExerciseUniqueWord extends Activity implements View.OnClickListener {
    private UniqueWordTraining training;
    private TextView wordNameView;
    private TextView textViewMist;
    private TextView transToEnter;
    private int allSpaces;
    private int emptySpaces;
    private int step;
    private List<String> letters;
    private List<Button> letterArray;
    private Animation animBtnDisappear;
    private Animation animShake;
    private List<Integer> moves = new ArrayList<>();
    private ImageButton clear;
    private String firstTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_trans_by_word);

        init();
        showTraining();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.quit):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.quit_training_head))
                        .setMessage(getString(R.string.quit_training_body))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseUniqueWord.this.finish())
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                break;
            case (R.id.clearInput):
                step = 0;
                emptySpaces = allSpaces;
                transToEnter.setText(firstTrans);
                moves.clear();
                showLetters();
                break;
            case (R.id.deleteLast):
                if (moves.size() == 0) {
                    break;
                }
                int num = moves.get(moves.size() - 1);
                moves.remove(moves.size() - 1);
                if (letterArray.get(num).isEnabled()) {
                    step--;
                    emptySpaces++;
                    showLetters();
                    for (int i = 1; i < UniqueWordTraining.LETTERS_CNT; i++) {
                        letterArray.get(moves.get(moves.size() - i)).setEnabled(false);
                        letterArray.get(moves.get(moves.size() - i)).setVisibility(View.INVISIBLE);
                    }
                } else {
                    emptySpaces++;
                    letterArray.get(num).setEnabled(true);
                    letterArray.get(num).setVisibility(View.VISIBLE);
                }
                String now = transToEnter.getText().toString();
                int ind = now.lastIndexOf(letterArray.get(num).getText().toString());
                String prev = now.substring(0, ind) + "_" + now.substring(ind + 1);
                transToEnter.setText(prev);
                break;
            default:
                break;
        }
    }

    public void onLetterClick(View view) {
        view.startAnimation(animBtnDisappear);
        view.setVisibility(View.INVISIBLE);
        view.setEnabled(false);
        moves.add(getPos(view));
        transToEnter.setText(transToEnter.getText().toString().replaceFirst("_", String.valueOf(letters.get(step).charAt(moves.get(moves.size() - 1)))));
        emptySpaces--;
        if (emptySpaces == 0) {
            if (!training.check(transToEnter.getText().toString())) {
                clear.callOnClick();
                transToEnter.startAnimation(animShake);
            } else {
                //TODO OK next word
                clear.callOnClick();
                showTraining();
            }
        } else {
            if (moves.size() % UniqueWordTraining.LETTERS_CNT == 0) {
                step++;
                showLetters();
            }
        }
    }

    private Integer getPos(View view) {
        for (int i = 0; i < UniqueWordTraining.BUTTONS_CNT; i++){
            if (letterArray.get(i).equals(view)) {
                return i;
            }
        }
        return 0;
    }

    private void init() {
        Intent intent = getIntent();
        String mode = intent.getStringExtra(getString(R.string.mode));

        wordNameView = (TextView) findViewById(R.id.word_name);
        transToEnter = (TextView) findViewById(R.id.transToEnter);
        textViewMist = (TextView) findViewById(R.id.text_view_mist);

        animBtnDisappear = AnimationUtils.loadAnimation(this, R.anim.button_click_disappear);
        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        clear = (ImageButton) findViewById(R.id.clearInput);
        clear.setOnClickListener(this);
        ImageButton deleteLast = (ImageButton) findViewById(R.id.deleteLast);
        deleteLast.setOnClickListener(this);
        Button quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(this);
        letterArray = new ArrayList<>(UniqueWordTraining.BUTTONS_CNT);
        letterArray.add(0, (Button) findViewById(R.id.button1));
        letterArray.add(1, (Button) findViewById(R.id.button2));
        letterArray.add(2, (Button) findViewById(R.id.button3));
        letterArray.add(3, (Button) findViewById(R.id.button4));
        letterArray.add(4, (Button) findViewById(R.id.button5));
        letterArray.add(5, (Button) findViewById(R.id.button6));
        letterArray.add(6, (Button) findViewById(R.id.button7));
        letterArray.add(7, (Button) findViewById(R.id.button8));
        letterArray.add(8, (Button) findViewById(R.id.button9));
        letterArray.add(9, (Button) findViewById(R.id.button10));
        letterArray.add(10, (Button) findViewById(R.id.button11));
        letterArray.add(11, (Button) findViewById(R.id.button12));
        letterArray.add(12, (Button) findViewById(R.id.button13));
        letterArray.add(13, (Button) findViewById(R.id.button14));
        letterArray.add(14, (Button) findViewById(R.id.button15));

        DBConnector wordManager = new DBConnector(this,
                intent.getStringExtra(getString(R.string.dict_lang)),
                intent.getStringExtra(getString(R.string.native_lang)));
        Cursor cursor = wordManager.getAllWords();
        if (mode.equals(getString(R.string.trans_by_word))) {
            training = new TransByWord(cursor, intent.getStringExtra(getString(R.string.native_lang)));
        } else if (mode.equals(getString(R.string.word_by_trans))) {
            training = new WordByTrans(cursor, intent.getStringExtra(getString(R.string.dict_lang)));
        } else if (mode.equals(getString(R.string.correct_mistake))) {
            training = new CorrectMistakes(cursor, intent.getStringExtra(getString(R.string.dict_lang)));

        }
    }

    private void showTraining() {
        try {
            if (!training.getTraining()) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.oops))
                        .setMessage(getString(R.string.msg_not_enough_words))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseUniqueWord.this.finish())
                        .show();
            } else {
                try {
                    textViewMist.setText(training.getMistaken());
                    wordNameView.setText(training.getEntry());
                    firstTrans = training.getFirstWordToEnter();
                    transToEnter.setText(firstTrans);
                    emptySpaces = firstTrans.length() - firstTrans.replaceAll("_", "").length();
                    allSpaces = emptySpaces;
                    step = 0;
                    letters = training.getLetters();
                } catch (Exception e) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getString(R.string.oops))
//                            .setMessage(getString(R.string.msg_bad_language))
                            .setMessage(e.getMessage())
                            .setPositiveButton(getString(R.string.yes), (dialog, which) -> ExerciseUniqueWord.this.finish())
                            .show();
                    return;
                }
                showLetters();
            }
        } catch (Exception e) {
            //TODO animation finish
            ExerciseUniqueWord.this.finish();
        }
    }

    private void showLetters() {
        for (int i = 0; i < UniqueWordTraining.BUTTONS_CNT; i++) {
            letterArray.get(i).setText(String.valueOf(letters.get(step).charAt(i)));
            letterArray.get(i).setVisibility(View.VISIBLE);
            letterArray.get(i).setEnabled(true);
        }
    }
}
