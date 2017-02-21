package project.fedorova.polyglotte.exercise.double_cl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.exercise.DoubleWordTraining;

public class ChooseTransByWord extends DoubleWordTraining {
    private static final int CORRECT_RATE = 3;
    private static final int INCORRECT_RATE = 6;

    @Override
    public boolean check(String second) {
        return getTrWord().getMainTranslation().toLowerCase().equals(second.toLowerCase());
    }

    @Override
    public String getEntry() {
        return getTrWord().getWord();
    }

    public ChooseTransByWord(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }

    @Override
    public List<String> getChoicesVars() {
        List<String> tmp = new ArrayList<>(getChoicesCnt());
        for (int i = 0; i < getChoicesCnt(); i++) {
            tmp.add(i, getChoice(i).getMainTranslation());
        }
        return getButtons(tmp);
    }

    public void correctAnswer() {
        answer(CORRECT_RATE);
    }

    public void incorrectAnswer() {
        answer(INCORRECT_RATE);
    }
}
