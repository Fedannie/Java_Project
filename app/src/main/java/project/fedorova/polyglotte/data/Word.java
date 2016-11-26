package project.fedorova.polyglotte.data;

import java.util.HashSet;
import java.util.Set;

public class Word {
    private Set<String> themes;
    private String word;
    private Set<String> translations;

    public Word(String newWord, String... newTranslations) {
        word = newWord;
        translations = new HashSet<String>();
        themes = new HashSet<String>();
        for (String s : newTranslations) {
            translations.add(s);
        }
    }

    public void changeWord(String newWord) {
        word = newWord;
    }

    public void changeThemes(String... newThemes) {
        themes.clear();
        for (String s : newThemes) {
            if (!Theme.hasTheme(s)){
                Theme.addTheme(s);
            }
            themes.add(s);
        }
    }

    public void changeTranslations(String... newTranslations) {
        translations.clear();
        for (String s : newTranslations) {
            if (!Theme.hasTheme(s)){
                Theme.addTheme(s);
            }
            translations.add(s);
        }
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
}
