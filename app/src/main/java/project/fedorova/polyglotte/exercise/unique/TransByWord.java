package project.fedorova.polyglotte.exercise.unique;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import project.fedorova.polyglotte.exercise.UniqueWordTraining;

public class TransByWord extends UniqueWordTraining {
    private static final int CORRECT_RATE = 3;
    private static final int INCORRECT_RATE = 6;
    public TransByWord(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }
    @Override
    public List<String> getLetters() {
        return getLetters(getTrWord().getMainTranslation());
    }

    @Override
    public boolean check(String second) {
        return getTrWord().getMainTranslation().equalsIgnoreCase(second);
    }

    @Override
    public String getEntry() {
        return getTrWord().getWord();
    }

    @Override
    public String getFirstWordToEnter() throws Exception{
        return getWordToEnter(getTrWord().getMainTranslation().toLowerCase());
    }

    public void correctAnswer() {
        answer(CORRECT_RATE);
    }

    public void incorrectAnswer() {
        answer(INCORRECT_RATE);
    }
}