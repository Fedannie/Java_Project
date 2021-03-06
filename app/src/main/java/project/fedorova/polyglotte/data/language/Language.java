package project.fedorova.polyglotte.data.language;

import java.util.Arrays;
import java.util.List;

public enum Language {

    ALBANIAN("sq"),
    ARMENIAN("hy"),
    AZERBAIJANI("az"),
    ARABIC("ar"),
    BELARUSIAN("be"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    GERMAN("de"),
    GEORGIAN("ka"),
    GREEK("el"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    NORWEGIAN("no"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SERBIAN("sr"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SWEDISH("sv"),
    TURKISH("tr"),
    UKRAINIAN("uk");

    private final String language;

    Language(String pLanguage) {
        language = pLanguage;
    }
    
    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage) || l.name().equalsIgnoreCase(pLanguage)){
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return language;
    }
}