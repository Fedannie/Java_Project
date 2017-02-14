package project.fedorova.polyglotte.exercise.double_cl;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;
import project.fedorova.polyglotte.exercise.DoubleWordTraining;

public class Connect extends DoubleWordTraining {
    public static final int BUTTONS_COUNT = 10;
    private static final int VARIANTS = 5;
    private List<String> names;
    private List<String> trans;
    private List<Word> tr_words;
    private String translation;
    public Connect(Cursor cursor) {
        super(cursor);
        setNullPosition();
        int[][] new_buttons = {{3, 4, 5}, {2, 4, 5}, {3, 5}, {2, 4, 5}, {3, 4, 5}, {3, 4, 5}, {2, 4, 5}, {3, 5}, {2, 4, 5}, {3, 4, 5}};
        setButtons(new_buttons);
    }

    @Override
    public boolean getTraining() throws Exception {
        if (wordsCount() < 2) {
            return false;
        } else if (wordsCount() <= getPos()) {
            throw new NoSuchElementException("That's all.");
        } else {
            tr_words = choose(Math.min(wordsCount(), VARIANTS));
        }
        return true;
    }

    public int getChoicesCnt() {
        return tr_words.size();
    }

    public Word getChoice(int i) {
        return tr_words.get(i);
    }

    @Override
    public boolean check(String second) {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        return second.equals(getTrWord().getMainTranslation() + "\n" + readWriteManager.convertSetToString(getTrWord().getTranslations()));
    }

    public boolean check() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        return translation.equals(getTrWord().getMainTranslation() + "\n" + readWriteManager.convertSetToString(getTrWord().getTranslations()));
    }

    @Override
    public List<String> getChoicesVars() {
        names = new ArrayList<>(getChoicesCnt());
        trans = new ArrayList<>(getChoicesCnt());
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        for (int i = 0; i < getChoicesCnt(); i++) {
            names.add(i, getChoice(i).getWord());
            trans.add(i, getChoice(i).getMainTranslation() + "\n" + readWriteManager.convertSetToString(getChoice(i).getTranslations()));
        }
        Collections.shuffle(trans);
        return getButtons();
    }

    @Override
    public String getEntry() {
        return null;
    }

    private List<String> getButtons() {
        List<String> ans = new ArrayList<>(BUTTONS_COUNT);
        int pos_n = 0;
        int pos_t = 0;
        for (int i = 0; i < BUTTONS_COUNT; i++) {
            if (isActivated(i, names.size())) {
                if (i < 5) {
                    ans.add(i, names.get(pos_n));
                    pos_n++;
                } else {
                    ans.add(i, trans.get(pos_t));
                    pos_t++;
                }
            } else {
                ans.add(i, null);
            }
        }
        return ans;
    }

    public void setWord(int ind) {
        setWord(tr_words.get(ind));
    }

    public void setTranslation(String new_tr) {
        translation = new_tr;
    }
}
