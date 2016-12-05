package project.fedorova.polyglotte.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Word implements Serializable{
    private long ID = -1;
    private Set<String> themes;
    private String word;
    private Set<String> translations;

    public Word(long id, String newWord, String... newTranslations) {
        word = newWord;
        ID = id;
        translations = new HashSet<String>();
        themes = new HashSet<String>();
        for (String s : newTranslations) {
            translations.add(s);
        }
    }

    public Word(long id, String newWord, int length, String... newTranslations) {
        word = newWord;
        ID = id;
        translations = new HashSet<String>();
        themes = new HashSet<String>();
        int i = 0;
        for (String s : newTranslations){
            if (i < length){
                translations.add(s);
            } else {
                themes.add(s);
            }
            i++;
        }
    }

    public void changeWord(String newWord) {
        word = newWord;
    }

    public void changeThemes(String... newThemes) {
        themes.clear();
        themes.addAll(Arrays.asList(newThemes));
    }

    public void changeTranslations(String... newTranslations) {
        translations.clear();
        translations.addAll(Arrays.asList(newTranslations));
    }

    public boolean checkTheme(String theme) {
        return themes.contains(theme);
    }

    public boolean checkTranslation(String translation) {
        return translations.contains(translation);
    }

    public String[] getTranslations() {
        return (String[]) translations.toArray();
    }

    public String[] getThemes() {
        return (String[]) themes.toArray();
    }

    public String getWord() {
        return word;
    }

    public long getID() {
        return ID;
    }
}
