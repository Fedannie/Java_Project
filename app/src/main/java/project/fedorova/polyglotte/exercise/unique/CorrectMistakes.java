package project.fedorova.polyglotte.exercise.unique;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CorrectMistakes extends WordByTrans {
    private static final int CORRECT_RATE = 2;
    private static final int INCORRECT_RATE = -6;

    private static final int PERCENT_MISTAKES = 45;
    public CorrectMistakes(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }
    @Override
    public String getMistaken(){
        Random random = new Random();
        String word = getTrWord().getWord();
        int cnt = 1 + random.nextInt(Math.max(PERCENT_MISTAKES * (word.replaceAll("'", "").replaceAll(" ", "").length() - 1), 100) / 100);
        cnt = Math.min(cnt, word.length());
        List<Integer> order = new ArrayList<>(cnt);
        for (int i = 0; i < word.length(); i++) {
            order.add(i, i);
        }
        Collections.shuffle(order);
        List<String> curAlpha = getAlpha();
        Collections.shuffle(curAlpha);
        for (int i = 0; i < cnt; i++) {
            if (word.charAt(i) != '\'' && word.charAt(i) != ' ') {
                word = word.substring(0, order.get(i)) + curAlpha.get(i) + word.substring(order.get(i) + 1);
            }
        }
        return word;
    }

    @Override
    public void correctAnswer() {
        answer(CORRECT_RATE);
    }

    @Override
    public void incorrectAnswer() {
        answer(INCORRECT_RATE);
    }
}
