package project.fedorova.polyglotte.translator.language;

import android.util.Log;

import project.fedorova.polyglotte.translator.translate.Translate;

public enum Language {

    DEFAULT(Translate.DEFAULT_LANG.toString()),
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

    String language;

    Language(String pLanguage) {
        language = pLanguage;
    }

    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        return null;
    }

    public static String getLanguageCode(String lang) throws Exception{
        switch (lang) {
            case "Albanian": return Language.ALBANIAN.toString();
            case "Armenian": return Language.ARMENIAN.toString();
            case "Azerbaijani": return Language.AZERBAIJANI.toString();
            case "Arabic": return Language.ARABIC.toString();
            case "Belarusian": return Language.BELARUSIAN.toString();
            case "Bulgarian": return Language.BULGARIAN.toString();
            case "Catalan": return Language.CATALAN.toString();
            case "Croatian": return Language.CROATIAN.toString();
            case "Czech": return Language.CZECH.toString();
            case "Danish": return Language.DANISH.toString();
            case "Dutch": return Language.DUTCH.toString();
            case "English": return Language.ENGLISH.toString();
            case "Estonian": return Language.ESTONIAN.toString();
            case "Finnish": return Language.FINNISH.toString();
            case "French": return Language.FRENCH.toString();
            case "German": return Language.GERMAN.toString();
            case "Georgian": return Language.GEORGIAN.toString();
            case "Greek": return Language.GREEK.toString();
            case "Hungarian": return Language.HUNGARIAN.toString();
            case "Italian": return Language.ITALIAN.toString();
            case "Latvian": return Language.LATVIAN.toString();
            case "Lithuanian": return Language.LITHUANIAN.toString();
            case "Macedonian": return Language.MACEDONIAN.toString();
            case "Norwegian": return Language.NORWEGIAN.toString();
            case "Polish": return Language.POLISH.toString();
            case "Portuguese": return Language.PORTUGUESE.toString();
            case "Romanian": return Language.ROMANIAN.toString();
            case "Russian": return Language.RUSSIAN.toString();
            case "Serbian": return Language.SERBIAN.toString();
            case "Slovak": return Language.SLOVAK.toString();
            case "Slovenian": return Language.SLOVENIAN.toString();
            case "Spanish": return Language.SPANISH.toString();
            case "Swedish": return Language.SWEDISH.toString();
            case "Turkish": return Language.TURKISH.toString();
            case "Ukrainian": return Language.UKRAINIAN.toString();
            default: throw new Exception();
        }
    }

    @Override
    public String toString() {
        return language;
    }
}