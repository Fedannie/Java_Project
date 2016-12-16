package project.fedorova.polyglotte.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Word implements Serializable{
    private UUID ID;
    private Set<String> themes = new HashSet<>();
    private String word;
    private String mainTranslation;
    private Set<String> translations = new HashSet<>();
    private Set<String> examples = new HashSet<>();

    public Word(UUID id, String newWord, String newMainTranslation, Set<String> newTranslations, Set<String> newThemes, Set<String> newExamples) {
        word = newWord;
        mainTranslation = newMainTranslation;
        ID = id;
        if (newTranslations != null) {
            translations = newTranslations;
        }
        if (newThemes != null) {
            themes = newThemes;
        }
        if (newExamples != null) {
            examples = newExamples;
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
        if (newThemes != null) {
            themes = newThemes;
        }
    }

    public void changeTranslations(Set<String> newTranslations) {
        translations.clear();
        if (newTranslations != null) {
            translations = newTranslations;
        }
    }

    public void changeExamples(Set<String> newExamples) {
        examples.clear();
        if (newExamples != null) {
            examples = newExamples;
        }
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

    public Set<String> getExamples() {
        return examples;
    }

    public String getWord() {
        return word;
    }

    public UUID getID() {
        return ID;
    }
}
