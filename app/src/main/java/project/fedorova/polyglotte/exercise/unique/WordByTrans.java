package project.fedorova.polyglotte.exercise.unique;

import android.content.Context;

import java.util.List;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.exercise.UniqueWordTraining;

public class WordByTrans extends UniqueWordTraining {
    private static final int CORRECT_RATE = 4;
    private static final int INCORRECT_RATE = -6;

    public WordByTrans(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }

    @Override
    public boolean check(String second) {
        return getTrWord().getWord().equalsIgnoreCase(second);
    }

    @Override
    public List<String> getLetters() {
        return getLetters(getTrWord().getWord());
    }

    @Override
    public String getEntry() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        return getTrWord().getMainTranslation() + "\n" +
                readWriteManager.convertSetToString(getTrWord().getTranslations());
    }

    @Override
    public String getFirstWordToEnter() throws Exception {
        return getWordToEnter(getTrWord().getWord().toLowerCase());
    }

    public void correctAnswer() {
        answer(CORRECT_RATE);
    }

    public void incorrectAnswer() {
        answer(INCORRECT_RATE);
    }
}
