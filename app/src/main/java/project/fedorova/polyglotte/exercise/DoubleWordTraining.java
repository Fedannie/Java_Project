package project.fedorova.polyglotte.exercise;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import project.fedorova.polyglotte.data.Word;

public abstract class DoubleWordTraining extends Training {
    private List<Word> words;
    public abstract boolean check(Word first, String second);
    public DoubleWordTraining(Cursor cursor, int num) {
        super(cursor);
        words = new ArrayList<>(num);
    }

    public boolean getTraining()  throws Exception{
        try {
            words = super.choose(words.size());
            incPos();
        } catch (NullPointerException e) {
            throw e;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}
