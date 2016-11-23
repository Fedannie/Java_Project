package project.fedorova.polyglotte.translator.translate;

import java.net.URL;
import java.net.URLEncoder;

import project.fedorova.polyglotte.translator.ApiKey;
import project.fedorova.polyglotte.translator.YandexTranslatorApi;
import project.fedorova.polyglotte.translator.language.Language;

public class Translate extends YandexTranslatorApi {

    private static final String SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static final String TRANSLATION_LABEL = "text";
    public static String DEFAULT_LANG = "en";

    private Translate(){};

    /**
     * Set new default language
     *
     * @param def new default language
     */
    public void changeDefaultLang(String def) {
        DEFAULT_LANG = def;
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
    public static String execute(final String text, final Language from, final Language to) throws Exception {
        setApiKey();
        validServiceState(text);
        final String params = PARAM_KEY
                            + URLEncoder.encode(apiKey,ENCODING)
                            + PARAM_LANGS
                            + URLEncoder.encode(from.toString(),ENCODING)
                            + URLEncoder.encode("-",ENCODING)
                            + URLEncoder.encode(to.toString(),ENCODING)
                            + PARAM_TEXT
                            + URLEncoder.encode(text,ENCODING);
        final URL url = new URL(SERVICE_URL + params);
        String res = getPropArrString(url, TRANSLATION_LABEL).trim();
        return res.substring(2, res.length() - 2);
    }

    private static void validServiceState(final String text) throws Exception {
        final int byteLength = text.getBytes(ENCODING).length;
        if (byteLength > 10240) {
            throw new RuntimeException("TEXT_TOO_LARGE");
        }
        validServiceState();
    }
}
