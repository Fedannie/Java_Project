package project.fedorova.polyglotte.translator.translate;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import project.fedorova.polyglotte.translator.ApiKey;
import project.fedorova.polyglotte.translator.YandexTranslatorApi;
import project.fedorova.polyglotte.translator.language.Language;

public class Translate extends YandexTranslatorApi {
    private static volatile Translate instance;

    private static final String SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static final String TRANSLATION_LABEL = "text";
    public Language DEFAULT_LANG = Language.ENGLISH;
    private Map<String, String> langsCodes;
    private Translate(){
        fillMap();
    };

    /**
     * Set new default language
     *
     * @param def new default language
     */
    public void changeDefaultLang(String def) {
        DEFAULT_LANG = Language.fromString(def);
    }

    /**
     * Translates text from a given Language to another given Language using Yandex.
     *
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public String execute(final String text, final Language from, final Language to) throws Exception {
        setApiKey();
        validServiceState(text);
        final String params = PARAM_KEY
                            + URLEncoder.encode(getApiKey(), ENCODING)
                            + PARAM_LANGS
                            + URLEncoder.encode(from.toString(), ENCODING)
                            + URLEncoder.encode("-", ENCODING)
                            + URLEncoder.encode(to.toString(), ENCODING)
                            + PARAM_TEXT
                            + URLEncoder.encode(text, ENCODING);
        final URL url = new URL(SERVICE_URL + params);
        String res = getPropArrString(url, TRANSLATION_LABEL).trim();
        return res.substring(2, res.length() - 2);
    }

    private void validServiceState(final String text) throws Exception {
        final int byteLength = text.getBytes(ENCODING).length;
        if (byteLength > 10240) {
            throw new RuntimeException("TEXT_TOO_LARGE");
        }
        validServiceState();
    }

    private void fillMap() {
        langsCodes = new HashMap<String, String>();
        langsCodes.put("Albanian", Language.ALBANIAN.toString());
        langsCodes.put("Armenian", Language.ARMENIAN.toString());
        langsCodes.put("Azerbaijani", Language.AZERBAIJANI.toString());
        langsCodes.put("Arabic", Language.ARABIC.toString());
        langsCodes.put("Belarusian", Language.BELARUSIAN.toString());
        langsCodes.put("Bulgarian", Language.BULGARIAN.toString());
        langsCodes.put("Catalan", Language.CATALAN.toString());
        langsCodes.put("Croatian", Language.CROATIAN.toString());
        langsCodes.put("Czech", Language.CZECH.toString());
        langsCodes.put("Danish", Language.DANISH.toString());
        langsCodes.put("Dutch", Language.DUTCH.toString());
        langsCodes.put("English", Language.ENGLISH.toString());
        langsCodes.put("Estonian", Language.ESTONIAN.toString());
        langsCodes.put("Finnish", Language.FINNISH.toString());
        langsCodes.put("French", Language.FRENCH.toString());
        langsCodes.put("German", Language.GERMAN.toString());
        langsCodes.put("Georgian", Language.GEORGIAN.toString());
        langsCodes.put("Greek", Language.GREEK.toString());
        langsCodes.put("Hungarian", Language.HUNGARIAN.toString());
        langsCodes.put("Italian", Language.ITALIAN.toString());
        langsCodes.put("Latvian", Language.LATVIAN.toString());
        langsCodes.put("Lithuanian", Language.LITHUANIAN.toString());
        langsCodes.put("Macedonian", Language.MACEDONIAN.toString());
        langsCodes.put("Norwegian", Language.NORWEGIAN.toString());
        langsCodes.put("Polish", Language.POLISH.toString());
        langsCodes.put("Portuguese", Language.PORTUGUESE.toString());
        langsCodes.put("Romanian", Language.ROMANIAN.toString());
        langsCodes.put("Russian", Language.RUSSIAN.toString());
        langsCodes.put("Serbian", Language.SERBIAN.toString());
        langsCodes.put("Slovak", Language.SLOVAK.toString());
        langsCodes.put("Slovenian", Language.SLOVENIAN.toString());
        langsCodes.put("Spanish", Language.SPANISH.toString());
        langsCodes.put("Swedish", Language.SWEDISH.toString());
        langsCodes.put("Turkish", Language.TURKISH.toString());
        langsCodes.put("Ukrainian", Language.UKRAINIAN.toString());
    }

    public String getLanguageCode(String lang) throws NoSuchElementException {
        String res =  langsCodes.get(lang);
        if (res == null){
            throw new NoSuchElementException();
        }
        return res;
    }

    public static Translate getInstance() {
        Translate localInstance = instance;
        if (localInstance == null) {
            synchronized (Translate.class){
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Translate();
                }
            }
        }
        return localInstance;
    }
}
