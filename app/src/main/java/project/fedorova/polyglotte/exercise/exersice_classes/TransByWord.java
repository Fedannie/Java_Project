package project.fedorova.polyglotte.exercise.exersice_classes;

import android.database.Cursor;

import java.util.List;

import project.fedorova.polyglotte.exercise.UniqueWordTraining;

public class TransByWord extends UniqueWordTraining {
    public TransByWord(Cursor cursor, String lang) {
        super(cursor, lang);
    }

    @Override
    public List<String> getLetters() {
        return getLetters(getTrWord().getMainTranslation().toLowerCase().replaceAll(" ", "").replaceAll("'", ""));
    }

    @Override
    public boolean check(String second) {
        return getTrWord().getMainTranslation().toLowerCase().equals(second.toLowerCase());
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