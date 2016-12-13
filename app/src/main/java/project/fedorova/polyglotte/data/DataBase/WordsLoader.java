package project.fedorova.polyglotte.data.DataBase;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import java.util.Set;

public class WordsLoader extends AsyncTaskLoader<Cursor>{
    private final DBConnector wordsManager;
    private Cursor data;
    private Set<String> themes;

    public WordsLoader(Context context, String langFrom, String langTo, Set<String> newThemes) {
        super(context);
        wordsManager = new DBConnector(context, langFrom, langTo);
        themes = newThemes;
    }

    @Override
    protected  void onReset() {
        super.onReset();
        if (data != null) {
            data.close();
        }
        data = null;
    }

    @Override
    protected  void onStartLoading() {
        if (takeContentChanged() || data == null) {
            forceLoad();
        } else {
            deliverResult(data);
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (themes == null) {
            data = wordsManager.getAllWords();
        } else {
            data = wordsManager.getWordsByTheme(themes);
        }
        return data;
    }
}
