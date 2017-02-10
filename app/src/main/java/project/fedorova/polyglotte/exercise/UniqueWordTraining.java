package project.fedorova.polyglotte.exercise;

import android.database.Cursor;

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

    public abstract String getEntry();

    public abstract String getFirstWordToEnter() throws Exception;

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
            word = getNextWord();
            incPos();
        }
        return true;
    }

    protected Word getTrWord() {
        return word;
    }

    protected String getWordToEnter(String guess) throws Exception {
        String res = "";
        List<String> alphabet = alpha.getAlphabet(Language.fromString(lang).toString());
        if (alphabet == null) {
            throw new Exception();
        }
        for (int i = 0; i < guess.length(); i++) {
            if (!alphabet.contains(String.valueOf(guess.charAt(i)))) {
                res += guess.charAt(i);
            } else {
                res += "_";
            }
        }
        return res;
    }

    public abstract List<String> getLetters();

    protected List<String> getLetters(String guess) {
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
            String ans = "";
            for (String letter : shuffled){
                ans += letter;
            }
            res.set(i, ans);
        }
        return res;
    }
}
