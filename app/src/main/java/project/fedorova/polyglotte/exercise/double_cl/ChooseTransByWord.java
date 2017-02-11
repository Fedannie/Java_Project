package project.fedorova.polyglotte.exercise.double_cl;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.exercise.DoubleWordTraining;

public class ChooseTransByWord extends DoubleWordTraining {
    @Override
    public boolean check(String second) {
        return getTrWord().getMainTranslation().toLowerCase().equals(second.toLowerCase());
    }

    @Override
    public String getEntry() {
        return getTrWord().getWord();
    }

    public ChooseTransByWord(Cursor cursor) {
        super(cursor);
    }

    @Override
    public List<String> getChoicesVars() {
        List<String> ans = new ArrayList<>(BUTTONS_COUNT);
        int pos = 0;
        for (int i = 0; i < BUTTONS_COUNT; i++) {
            if (isActivated(i, getChoicesCnt())) {
                ans.add(i, getChoice(pos).getMainTranslation());
                pos++;
            } else {
                ans.add(i, null);
            }
        }
        return ans;
    }
}
