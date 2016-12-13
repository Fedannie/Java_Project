package project.fedorova.polyglotte.data;

public class PreferenceVars {
    private static volatile PreferenceVars instance;

    public static final String NATIVE_LANGUAGE = "NATIVE_LANG";
    private String nativeLang = null;
    public static final String DICT_LANGUAGE = "DICT_LANG";
    private String dictLang = null;
    public static final String DEFAULT_LANG = "English";
    public static final String FIRST_TIME = "first time";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String WORD_INDEX = "word index";
    public static final String IF_EDIT = "if edit";

    public String getNativeLang() {
        if (nativeLang == null) {
            return DEFAULT_LANG;
        } else {
            return nativeLang;
        }
    }

    public String getDictLang() {
        if (dictLang == null) {
            return DEFAULT_LANG;
        } else {
            return dictLang;
        }
    }

    public void setNativeLang(String newNativeLang) {
        nativeLang = newNativeLang;
    }

    public void setDictLang(String newDictLang) {
        dictLang = newDictLang;
    }

    private PreferenceVars() {}

    public static PreferenceVars getInstance() {
        PreferenceVars localInstance = instance;
        if (localInstance == null) {
            synchronized (PreferenceVars.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PreferenceVars();
                }
            }
        }
        return localInstance;
    }
}
