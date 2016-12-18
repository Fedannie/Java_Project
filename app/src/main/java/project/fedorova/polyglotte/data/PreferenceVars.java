package project.fedorova.polyglotte.data;

public class PreferenceVars {
    private static volatile PreferenceVars instance;

    public static final String NATIVE_LANGUAGE = "NATIVE_LANG";
    public static final String DICT_LANGUAGE = "DICT_LANG";
    public static final String DEFAULT_LANG = "English";
    public static final String FIRST_TIME = "first time";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String WORD_INDEX = "word index";
    public static final String IF_EDIT = "if edit";
    public static final String PHRASE = "phrase";
    public static final String TRANS = "translation";
    public static final String NATIVE_LANG_CHANGED = "native lang changed";

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
