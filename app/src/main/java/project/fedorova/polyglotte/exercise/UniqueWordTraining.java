package project.fedorova.polyglotte.exercise;

import android.database.Cursor;

import java.util.List;

import project.fedorova.polyglotte.data.Word;

public abstract class UniqueWordTraining extends Training {
    protected Word word;
    protected String lang;
    public abstract boolean check(String second);
    public UniqueWordTraining(Cursor cursor, String language) {
        super(cursor);
        lang = language;
    }

    public boolean getTraining() throws Exception{
        if (wordsCount() == 0) {
            return false;
        } else if (wordsCount() <= getPos()) {
            throw new NullPointerException("That's all.");
        } else {
            word = getWord();
            incPos();
        }
        return true;
    }

    public Word getWord() {
        return word;
    }

    public abstract String getWordToEnter() throws Exception;

    public abstract List<String> getLetters();
}
