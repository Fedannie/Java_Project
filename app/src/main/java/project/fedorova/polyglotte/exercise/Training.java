package project.fedorova.polyglotte.exercise;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;
import project.fedorova.polyglotte.data.db.DBConnector;

abstract class Training {
    private List<Word> wordList = new ArrayList<>();
    List <Word> choose(int n) throws IndexOutOfBoundsException{
        Collections.shuffle(wordList);
        List<Word> res;
        try {
            res = wordList.subList(0, n);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Not enough words");
        }
        return res;
    }

    Training(Cursor cursor) {
        cursor.moveToFirst();
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        if (cursor.getCount() > 0) {
            wordList.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_EXAMPLES))));
        }
        while (cursor.moveToNext()) {
            wordList.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_EXAMPLES))));
        }
    }
}
