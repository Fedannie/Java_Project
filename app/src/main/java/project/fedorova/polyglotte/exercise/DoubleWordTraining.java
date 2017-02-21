package project.fedorova.polyglotte.exercise;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.fedorova.polyglotte.data.Word;

public abstract class DoubleWordTraining extends Training {
    private Word word;
    private List<Word> choices = new ArrayList<>(VARIANTS);
    private static final int VARIANTS = 6;
    public static final int BUTTONS_COUNT = 9;
    private int[][] buttons = {{4,5,6},{3,6},{4,5,6},{2,3},{5},{2,3},{4,5,6},{6},{4,5,6}};

    public abstract boolean check(String second);

    public abstract List<String> getChoicesVars();

    public abstract String getEntry();

    public boolean getTraining() throws Exception{
        if (wordsCount() < 2) {
            return false;
        } else if (wordsCount() <= getPos()) {
            throw new NullPointerException("That's all.");
        } else {
            word = getNextWord();
            choices = choose(Math.min(wordsCount() - 1, VARIANTS - 1));
            choices.add(word);
            Collections.shuffle(choices);
            incPos();
        }
        return true;
    }

    protected int getChoicesCnt() {
        return choices.size();
    }

    protected Word getTrWord() {
        return word;
    }

    protected  Word getChoice(int pos) {
        return choices.get(pos);
    }

    public DoubleWordTraining(Context cntx, String lang_from, String lang_to) {
        super(cntx, lang_from, lang_to);
    }

    protected boolean isActivated(int number, int cnt) {
        for (int i : buttons[number]){
            if (i == cnt){
                return true;
            }
        }
        return false;
    }

    protected List<String> getButtons(List<String> ch) {
        List<String> ans = new ArrayList<>(BUTTONS_COUNT);
        int pos = 0;
        for (int i = 0; i < BUTTONS_COUNT; i++) {
            if (isActivated(i, choices.size())) {
                ans.add(i, ch.get(pos));
                pos++;
            } else {
                ans.add(i, null);
            }
        }
        return ans;
    }

    protected void setWord(Word new_w){
        word = new_w;
    }

    protected void setButtons(int[][] new_b){
        buttons = new_b;
    }

    @Override
    public void answer(int rate) {
        word.incKnowledge(rate);
        dbConnector.updateWord(word);
    }
}
