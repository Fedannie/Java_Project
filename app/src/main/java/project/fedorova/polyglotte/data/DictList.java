package project.fedorova.polyglotte.data;

import java.util.HashSet;
import java.util.Set;

public class DictList {
    private static volatile DictList instance;

    private Set<String> dictList = new HashSet<>();

    public boolean addDict(String language) {
        if (!dictList.contains(language)) {
            dictList.add(language);
            return true;
        }
        return false;
    }

    public Set<String> getDictList() {
        return dictList;
    }

    public void deleteDict(String language){
        dictList.remove(language);
    }

    public boolean empty() {
        return dictList.size() > 0;
    }

    private DictList(){}

    public static DictList getInstance() {
        DictList localInstance = instance;
        if (localInstance == null) {
            synchronized (DictList.class){
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DictList();
                }
            }
        }
        return localInstance;
    }
}
