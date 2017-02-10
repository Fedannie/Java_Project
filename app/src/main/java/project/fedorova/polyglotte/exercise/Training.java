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
    private int position = 0;

    List <Word> choose(int n) throws IndexOutOfBoundsException{
        if (position == wordList.size()) {
            throw new NullPointerException("That's all.");
        }
        List<Word> shuffled = new ArrayList<>();
        if (position != 0) {
            shuffled = wordList.subList(0, position);
        }
        if (position + 1 < wordList.size()) {
            shuffled.addAll(wordList.subList(position + 1, wordList.size()));
        }
        Collections.shuffle(shuffled);
        try {
            shuffled = shuffled.subList(0, n);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That's all.");
        }
        return shuffled;
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
        Collections.shuffle(wordList);
    }

    public abstract boolean getTraining() throws Exception;

    public int wordsCount() {
        return wordList.size();
    }

    public int getPos(){
        return position;
    }

    public void incPos() {
        position++;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public Word getWord(int i) {
        return wordList.get(i);
    }

    public Word getWord() {
        return wordList.get(position);
        //incPos();
    }
}
