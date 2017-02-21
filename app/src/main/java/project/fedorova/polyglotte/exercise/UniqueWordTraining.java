package project.fedorova.polyglotte.exercise;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.fedorova.polyglotte.data.Word;
import project.fedorova.polyglotte.data.language.Alphabet;
import project.fedorova.polyglotte.data.language.Language;

public abstract class UniqueWordTraining extends Training {
    private Word word;
    private Alphabet alpha = Alphabet.getInstance();
    private String lang;
    public final static int LETTERS_CNT = 7;
    public final static int BUTTONS_CNT = 15;

    public abstract boolean check(String second);

    public abstract List<String> getLetters();

    public abstract String getEntry();

    public abstract String getFirstWordToEnter() throws Exception;

    public String getMistaken() {
        return "";
    }

    public UniqueWordTraining(Context cntx, String from_lang, String to_lang) {
        super(cntx, from_lang, to_lang);
        lang = to_lang;
    }

    public boolean getTraining() throws Exception{
        if (wordsCount() == 0) {
            return false;
        } else if (wordsCount() <= getPos()) {
            throw new NullPointerException("That's all.");
        } else {
            word = getNextWord();
            incPos();
        }
        return true;
    }

    protected Word getTrWord() {
        return word;
    }

    protected String getWordToEnter(String guess) throws Exception {
        StringBuilder res = new StringBuilder();
        List<String> alphabet = alpha.getAlphabet(Language.fromString(lang).toString());
        if (alphabet == null) {
            throw new Exception();
        }
        for (int i = 0; i < guess.length(); i++) {
            if (!alphabet.contains(String.valueOf(guess.charAt(i)))) {
                res.append(guess.charAt(i));
            } else {
                res.append("_");
            }
        }
        return res.toString();
    }

    protected List<String> getLetters(String guess) {
        guess = guess.toLowerCase().replaceAll(" ", "").replaceAll("'", "");
        Random random = new Random();
        int size = (guess.length() + (LETTERS_CNT - 1)) / LETTERS_CNT;
        List<String> res = new ArrayList<>();
        List<String> alphabet = alpha.getAlphabet(Language.fromString(lang).toString());
        for (int i = 0; i < size; i++) {
            if (guess.length() >= LETTERS_CNT) {
                res.add(i, guess.substring(0, LETTERS_CNT));
                guess = guess.substring(LETTERS_CNT);
            } else {
                res.add(i, guess);
            }
            int tmp = res.get(i).length();
            for (int j = tmp; j < BUTTONS_CNT; j++) {
                res.set(i, res.get(i) + alphabet.get(random.nextInt(alphabet.size())));
            }
            List<String> shuffled = Arrays.asList(res.get(i).split(""));
            Collections.shuffle(shuffled);
            StringBuilder ans = new StringBuilder();
            for (String letter : shuffled){
                ans.append(letter);
            }
            res.set(i, ans.toString());
        }
        return res;
    }

    protected List<String> getAlpha() {
        return alpha.getAlphabet(lang);
    }

    //TODO move to training
    @Override
    public void answer(int rate) {
        word.incKnowledge(rate);
        dbConnector.updateWord(word);
    }
}
