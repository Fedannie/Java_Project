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
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        switch (pLanguage.toUpperCase()) {
            case ("ALBANIAN"): return ALBANIAN;
            case ("ARMENIAN"): return ARMENIAN;
            case ("AZERBAIJANI"): return AZERBAIJANI;
            case ("ARABIC"): return ARABIC;
            case ("BELARUSIAN"): return BELARUSIAN;
            case ("BULGARIAN"): return BULGARIAN;
            case ("CATALAN"): return CATALAN;
            case ("CROATIAN"): return CROATIAN;
            case ("CZECH"): return CZECH;
            case ("DANISH"): return DANISH;
            case ("DUTCH"): return DUTCH;
            case ("ENGLISH"): return ENGLISH;
            case ("ESTONIAN"): return ESTONIAN;
            case ("FINNISH"): return FINNISH;
            case ("FRENCH"): return FRENCH;
            case ("GERMAN"): return GERMAN;
            case ("GEORGIAN"): return GEORGIAN;
            case ("GREEK"): return GREEK;
            case ("HUNGARIAN"): return HUNGARIAN;
            case ("ITALIAN"): return ITALIAN;
            case ("LATVIAN"): return LATVIAN;
            case ("LITHUANIAN"): return LITHUANIAN;
            case ("MACEDONIAN"): return MACEDONIAN;
            case ("NORWEGIAN"): return NORWEGIAN;
            case ("POLISH"): return POLISH;
            case ("PORTUGUESE"): return PORTUGUESE;
            case ("ROMANIAN"): return ROMANIAN;
            case ("RUSSIAN"): return RUSSIAN;
            case ("SERBIAN"): return SERBIAN;
            case ("SLOVAK"): return SLOVAK;
            case ("SLOVENIAN"): return SLOVENIAN;
            case ("SPANISH"): return SPANISH;
            case ("SWEDISH"): return SWEDISH;
            case ("TURKISH"): return TURKISH;
            case ("UKRAINIAN"): return UKRAINIAN;
        }
        return null;
    }

    @Override
    public String toString() {
        return language;
    }
}