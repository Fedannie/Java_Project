package project.fedorova.polyglotte.exercise.unique;

import android.database.Cursor;

import java.util.List;

import project.fedorova.polyglotte.exercise.UniqueWordTraining;

public class TransByWord extends UniqueWordTraining {
    public TransByWord(Cursor cursor, String lang) {
        super(cursor, lang);
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
}