package project.fedorova.polyglotte.exercise.double_cl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.exercise.DoubleWordTraining;

public class ChooseWordByTrans extends DoubleWordTraining {
    private static final int CORRECT_RATE = 4;
    private static final int INCORRECT_RATE = -6;

    public ChooseWordByTrans(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }

    @Override
    public boolean check(String second) {
        return getTrWord().getWord().toLowerCase().equals(second.toLowerCase());
    }

    @Override
    public List<String> getChoicesVars() {
        List<String> tmp = new ArrayList<>(getChoicesCnt());
        for (int i = 0; i < getChoicesCnt(); i++) {
            tmp.add(i, getChoice(i).getWord());
        }
        return getButtons(tmp);
    }

    @Override
    public String getEntry() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        return getTrWord().getMainTranslation() + "\n" +
                readWriteManager.convertSetToString(getTrWord().getTranslations());
    }

    public void correctAnswer() {
        answer(CORRECT_RATE);
    }

    public void incorrectAnswer() {
        answer(INCORRECT_RATE);
    }
}
