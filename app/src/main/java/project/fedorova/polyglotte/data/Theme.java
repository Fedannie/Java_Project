package project.fedorova.polyglotte.data;

import java.util.ArrayList;

public class Theme {
    private String title;
    private ArrayList<String> phrases_dict;
    private ArrayList<String> phrases_nat;

    public Theme(String newTitle, ArrayList<String> newPhrases_dict, ArrayList<String> newPhrases_nat) {
        title = newTitle;
        phrases_dict = newPhrases_dict;
        phrases_nat = newPhrases_nat;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getPhrases_dict() {
        return phrases_dict;
    }

    public ArrayList<String> getPhrases_nat() {
        return phrases_nat;
    }
}
