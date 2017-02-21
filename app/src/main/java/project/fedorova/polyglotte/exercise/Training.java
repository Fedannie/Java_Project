package project.fedorova.polyglotte.exercise;

import android.content.Context;
import android.database.Cursor;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;
import project.fedorova.polyglotte.data.db.DBConnector;

public abstract class Training {
    private Word word;
    public static final int LIVES_CNT = 5;
    private List<Word> wordList = new ArrayList<>();
    private int correct_cnt = 0;
    private int position = 0;
    DBConnector dbConnector;
    public abstract boolean getTraining() throws Exception;

    protected void setWord(Word newWord) {
        word = newWord;
    }

    protected Word getTrWord() {
        return word;
    }

    Training(Context cntx, String dict_from, String dict_to) {
        dbConnector = new DBConnector(cntx, dict_from, dict_to);
        Cursor cursor = dbConnector.getAllWords();
        cursor.moveToFirst();
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        if (cursor.getCount() > 0) {
            wordList.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_EXAMPLES)),
                    cursor.getInt(DBConnector.NUM_WORD_KNOWLEDGE)));
        }
        while (cursor.moveToNext()) {
            wordList.add(new Word(UUID.fromString(cursor.getString(DBConnector.NUM_WORD_ID)),
                    cursor.getString(DBConnector.NUM_WORD_TITLE),
                    cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_THEMES)),
                    readWriteManager.convertStringToSet(cursor.getString(DBConnector.NUM_WORD_EXAMPLES)),
                    cursor.getInt(DBConnector.NUM_WORD_KNOWLEDGE)));
        }
        Collections.shuffle(wordList);
    }

    protected List <Word> choose(int n) throws IndexOutOfBoundsException{
        if (position == wordList.size()) {
            throw new IndexOutOfBoundsException("That's all.");
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

    Word getNextWord() {
        return wordList.get(position);
    }

    void incPos() {
        position++;
    }

    protected void setNullPosition() {
        position = -1;
    }

    public void movePosition(int i) {
        for (int j = 0; j < i; j++) {
            incPos();
        }
    }

    protected int wordsCount() {
        return wordList.size();
    }

    public void incCorrect() {
        correct_cnt++;
    }

    public Pair<Integer, Integer> getCorrect() {
        return new Pair<>(correct_cnt, position);
    }

    public abstract void correctAnswer();

    public abstract void incorrectAnswer();

    public void answer(int rate) {
        word.incKnowledge(rate);
        dbConnector.updateWord(word);
    }
}
