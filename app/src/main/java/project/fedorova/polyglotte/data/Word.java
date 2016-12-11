package project.fedorova.polyglotte.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Word implements Serializable{
    private UUID ID;
    private Set<String> themes = null;
    private String word;
    private String mainTranslation;
    private Set<String> translations = null;

    public Word(UUID id, String newWord, String newMainTranslation, Set<String> newTranslations, Set<String> newThemes) {
        word = newWord;
        mainTranslation = newMainTranslation;
        ID = id;
        if (newTranslations == null) {
            translations = new HashSet<String>();
        } else {
            translations = newTranslations;
        }
        if (newThemes == null) {
            themes = new HashSet<String>();
        } else {
            themes = newThemes;
        }
    }

    public void changeWord(String newWord) {
        word = newWord;
    }

    public void changeMainTranslation(String translation) {
        mainTranslation = translation;
    }

    public void changeThemes(Set<String> newThemes) {
        themes.clear();
        themes = newThemes;
    }

    public void changeTranslations(Set<String> newTranslations) {
        translations.clear();
        translations = newTranslations;
    }

    public boolean hasTheme(String theme) {
        return themes.contains(theme);
    }

    public boolean checkTranslation(String translation) {
        return translations.contains(translation);
    }

    public Set<String> getTranslations() {
        return translations;
    }

    public String getMainTranslation() {
        return mainTranslation;
    }

    public Set<String> getThemes() {
        return themes;
    }

    public String getWord() {
        return word;
    }

    public UUID getID() {
        return ID;
    }
}
