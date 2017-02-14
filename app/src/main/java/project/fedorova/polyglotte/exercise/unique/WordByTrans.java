package project.fedorova.polyglotte.exercise.unique;

import android.database.Cursor;

import java.util.List;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.exercise.UniqueWordTraining;

public class WordByTrans extends UniqueWordTraining {

    public WordByTrans(Cursor cursor, String language) {
        super(cursor, language);
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
}
