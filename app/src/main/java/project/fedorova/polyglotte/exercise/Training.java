package project.fedorova.polyglotte.exercise;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;
import project.fedorova.polyglotte.data.db.DBConnector;

public abstract class Training {
    private List<Word> wordList = new ArrayList<>();
    private int position = 0;

    public abstract boolean getTraining() throws Exception;

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
        Collections.shuffle(wordList);
    }

    protected List <Word> choose(int n) throws IndexOutOfBoundsException{
        if (position == wordList.size()) {
            throw new NullPointerException("That's all.");
        }
        List<Word> shuffled = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            if (i != position) {
                shuffled.add(wordList.get(i));
            }
        }
        Collections.shuffle(shuffled);
        List<Word> ans;
        try {
             ans = shuffled.subList(0, n);
        } catch (Exception e) {
            throw new IndexOutOfBoundsException("That's all.");
        }
        return ans;
    }

    protected int getPos(){
        return position;
    }

    protected Word getNextWord() {
        return wordList.get(position);
    }

    protected void incPos() {
        position++;
    }

    protected int wordsCount() {
        return wordList.size();
    }
}
