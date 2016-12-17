package project.fedorova.polyglotte.data;

import java.util.UUID;

public class Phrase {
    private UUID id;
    private String phrase;
    private String translation;
    private String theme;

    public Phrase(UUID newId, String newPhrase, String newTranslation, String newTheme){
        id = newId;
        phrase = newPhrase;
        translation = newTranslation;
        theme = newTheme;
    }

    public String getTheme() {
        return theme;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getTranslation() {
        return translation;
    }

    public UUID getId() {
        return id;
    }

    public void setTranslation(String newTranslation) {
        translation = newTranslation;
    }

    public void setTheme(String newTheme) {
        theme = newTheme;
    }
}
