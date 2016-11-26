package project.fedorova.polyglotte.data;

import java.util.Set;

public class Theme {
    public static final String PATH = "themes/";
    private static Set<String> exist;

    public static String getPath(String theme) {
        return PATH + theme;
    }

    public static boolean hasTheme(String theme) {
        return exist.contains(theme);
    }

    public static void addTheme(String theme){
        exist.add(theme);
    }
}
