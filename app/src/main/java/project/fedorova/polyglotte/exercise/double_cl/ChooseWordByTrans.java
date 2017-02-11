package project.fedorova.polyglotte.exercise.double_cl;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.exercise.DoubleWordTraining;

public class ChooseWordByTrans extends DoubleWordTraining {
    public ChooseWordByTrans(Cursor cursor) {
        super(cursor);
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
}
