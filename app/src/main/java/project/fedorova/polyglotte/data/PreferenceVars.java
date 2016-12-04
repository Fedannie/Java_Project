package project.fedorova.polyglotte.data;

public class PreferenceVars {
    private static volatile PreferenceVars instance;

    public static final String NATIVE_LANGUAGE = "NATIVE_LANG";
    private String nativeLang;
    public static final String DICT_LANGUAGE = "DICT_LANG";
    private String dictLang;
    public static final String DEFAULT_LANG = "English";

    public String getNativeLang() {
        return nativeLang;
    }

    public String getDictLang() {
        return dictLang;
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
