package project.fedorova.polyglotte.exercise.exersice_classes;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.fedorova.polyglotte.data.language.Alphabet;
import project.fedorova.polyglotte.exercise.UniqueWordTraining;
import project.fedorova.polyglotte.data.language.Language;

public class TransByWord extends UniqueWordTraining {
    public TransByWord(Cursor cursor, String lang) {
        super(cursor, lang);
    }

    private Alphabet alpha = Alphabet.getInstance();

    @Override
    public String getWordToEnter() throws Exception {
        String translation = getWord().getMainTranslation();
        String res = "";
        List<String> alphabet = alpha.getAlphabet(Language.fromString(lang).toString());
        if (alphabet == null) {
            throw new Exception();
        }
        for (int i = 0; i < translation.length(); i++) {
            if (!alphabet.contains(String.valueOf(translation.charAt(i)))) {
                res += translation.charAt(i);
            } else {
                res += "_";
            }
        }
        return res;
    }

    @Override
    public List<String> getLetters() {
        Random random = new Random();
        String translation = getWord().getMainTranslation().replaceAll(" ", "").replaceAll("'", "");
        int size = (translation.length() + 8) / 9;
        List<String> res = new ArrayList<>();
        List<String> alphabet = alpha.getAlphabet(Language.fromString(lang).toString());
        String ttmp = "";
        for (int i = 0; i < alphabet.size(); i++) {
            ttmp += alphabet.get(i);
        }
        for (int i = 0; i < size; i++) {
            if (translation.length() >= 9) {
                res.add(i, translation.substring(0, 9));
                translation = translation.substring(9);
            } else {
                res.add(i, translation);
            }
            int tmp = res.get(i).length();
            for (int j = tmp; j < 15; j++) {
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

    @Override
    public boolean check(String second) {
        return word.getMainTranslation().equals(second);
    }

}