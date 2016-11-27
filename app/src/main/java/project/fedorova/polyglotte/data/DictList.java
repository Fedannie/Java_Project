package project.fedorova.polyglotte.data;

import java.util.HashSet;
import java.util.Set;

public class DictList {
    private static Set<String> dictList = new HashSet<String>();

    public static boolean addDict(String language) {
        if (!dictList.contains(language)) {
            dictList.add(language);
            return true;
        }
        return false;
    }

    public static String[] getDictList() {
        return dictList.toArray(new String[dictList.size()]);
    }

    public static void deleteDict(String language){
        dictList.remove(language);
    }

    public static boolean empty() {
        return dictList.size() > 0;
    }
}
