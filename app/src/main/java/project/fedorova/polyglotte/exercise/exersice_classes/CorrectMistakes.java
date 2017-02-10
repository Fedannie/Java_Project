package project.fedorova.polyglotte.exercise.exersice_classes;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CorrectMistakes extends WordByTrans {
    private static final int PERCENT_MISTAKES = 45;
    public CorrectMistakes(Cursor cursor, String language) {
        super(cursor, language);
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
}
