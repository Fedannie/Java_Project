package project.fedorova.polyglotte.data;

import java.util.ArrayList;
import java.util.Arrays;

public class PhraseList {
    private static volatile PhraseList instance;
    private String allToDivide = "Greating:Salut;Bonjour\n";
    private ArrayList<String> themes = new ArrayList<>();
    private ArrayList<ArrayList<String>> phrases = new ArrayList<>();
    private PhraseList() {
        String[] tmp = allToDivide.split("\n");
        for (String s : tmp) {
            String[] themeNPhrase = s.split(":");
            themes.add(themeNPhrase[0]);
            phrases.add(new ArrayList<>(Arrays.asList(themeNPhrase[1].split(";"))));
        }
    }

    public static PhraseList getInstance() {
        instance = null;
        PhraseList localInstance = instance;
        if (localInstance == null) {
            synchronized (PreferenceVars.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PhraseList();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<String> getThemes() {
        return themes;
    }

    public ArrayList<ArrayList<String>> getPhrases() {
        return phrases;
    }
}
