package project.fedorova.polyglotte.data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class DBConnector {

    private static final String DATABASE_NAME = "polyglotte.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String WORDS_TABLE_NAME = "words";

    private static final String WORD_ID = "_id";
    private static final String WORD_TITLE = "title";
    private static final String WORD_TRANSLATIONS = "translations";
    private static final String WORD_THEMES = "theme";

    private static final int NUM_WORD_ID = 0;
    private static final int NUM_WORD_TITLE = 1;
    private static final int NUM_WORD_TRANSLATIONS = 2;
    private static final int NUM_WORD_THEMES = 3;

    private static final String[] WORDS_COLUMNS = new String[] {WORD_ID, WORD_TITLE, WORD_TRANSLATIONS, WORD_THEMES};

    private static final String THEMES_TABLE_NAME = "themes";

    private static final String THEME_ID = "_id";
    private static final String THEME_TITLE = "title";

    private static final int NUM_THEME_ID = 0;
    private static final int NUM_THEME_TITLE = 1;

    private OpenHelper dbHelper;

    public DBConnector(Context context) {
        dbHelper = new OpenHelper(context);
    }

    public void insertWord(Word word) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            ContentValues cv = new ContentValues();
            cv.put(WORD_ID, word.getID());
            cv.put(WORD_TITLE, word.getWord());
            if (word.getTranslations() != null) {
                cv.put(WORD_TRANSLATIONS, readWriteManager.convertSetToString(word.getTranslations()));
            }
            if (word.getThemes() != null) {
                cv.put(WORD_THEMES, readWriteManager.convertSetToString(word.getThemes()));
            }
            database.insertOrThrow(WORDS_TABLE_NAME, null, cv);
            if (word.getThemes() != null) {
                for (String theme : word.getThemes()) {
                    ContentValues cvTheme = new ContentValues();
                    cvTheme.put(THEME_ID, word.getID());
                    cvTheme.put(THEME_TITLE, theme);
                    database.insertOrThrow(THEMES_TABLE_NAME, null, cvTheme);
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void updateWord(Word word) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            ContentValues cv = new ContentValues();
            cv.put(WORD_TITLE, word.getWord());
            if (word.getTranslations() != null) {
                cv.put(WORD_TRANSLATIONS, readWriteManager.convertSetToString(word.getTranslations()));
            }
            if (word.getThemes() != null) {
                cv.put(WORD_THEMES, readWriteManager.convertSetToString(word.getThemes()));
            }
            database.update(WORDS_TABLE_NAME, cv, WORD_ID + " = ?", new String[]{String.valueOf(word.getID())});
            database.delete(THEMES_TABLE_NAME, THEME_ID + " = ?", new String[]{String.valueOf(word.getID())});
            if (word.getThemes() != null) {
                for (String theme : word.getThemes()) {
                    ContentValues cvThemes = new ContentValues();
                    cvThemes.put(THEME_ID, word.getID());
                    cvThemes.put(THEME_TITLE, theme);
                    database.insertOrThrow(THEMES_TABLE_NAME, null, cvThemes);
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public int deleteAll() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        return database.delete(WORDS_TABLE_NAME, null, null);
    }

    public void delete(long id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(WORDS_TABLE_NAME, WORD_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public Word getWord(long id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        Cursor cursor = database.query(WORDS_TABLE_NAME, WORDS_COLUMNS, WORD_ID + " = ?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            cursor.moveToFirst();
            String word = cursor.getString(NUM_WORD_TITLE);
            Set<String> translations = readWriteManager.convertStringToArray(cursor.getString(NUM_WORD_TRANSLATIONS));
            Set<String> themes = readWriteManager.convertStringToArray(cursor.getString(NUM_WORD_THEMES));
            cursor.close();
            database.setTransactionSuccessful();
            database.endTransaction();
            return new Word(id, word, translations, themes);
        } else {
            return null;
        }
    }

    public Cursor getAllWords() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return database.query(WORDS_TABLE_NAME, WORDS_COLUMNS, null, null, null, null, WORD_TITLE);
    }

    public Cursor getWordsByTheme(Set<String> themes) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        StringBuilder selectionBuilder = null;
        String[] selectionArgs = themes.toArray(new String[]{});
        if (themes.size() > 0) {
            for (int i = 0; i < themes.size(); i++) {
                selectionBuilder.append(THEMES_TABLE_NAME + "." + THEME_TITLE + " = ? OR ");
            }
            selectionBuilder.append(THEMES_TABLE_NAME + "." + THEME_TITLE + " = ?");
        }
        String selection = selectionBuilder == null ? null : selectionBuilder.toString();
        return database.query(WORDS_TABLE_NAME + "inner join " +
                                       THEMES_TABLE_NAME + " on " +
                                       WORDS_TABLE_NAME + "." + " = " +
                                       THEMES_TABLE_NAME + "." + THEME_ID,
                                       WORDS_COLUMNS, selection, selectionArgs, WORD_ID,
                                       "count(" + THEMES_TABLE_NAME + "." + THEME_TITLE + ") = " +
                                        String.valueOf(themes.size()), null);
    }

    public Set<String> getThemesByWord(long id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(THEMES_TABLE_NAME, new String[]{THEME_TITLE}, THEME_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        Set<String> themes = new HashSet<>();
        while (cursor.moveToNext()) {
            themes.add(cursor.getString(NUM_THEME_TITLE));
        }
        cursor.close();
        return themes;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String wordsQuery = "CREATE TABLE " + WORDS_TABLE_NAME + " (" +
                    WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORD_TITLE + " TEXT NOT NULL, " +
                    WORD_TRANSLATIONS + " TEXT, " +
                    WORD_THEMES + " TEXT);";
            db.execSQL(wordsQuery);

            String themeQuery = "CREATE TABLE " + THEMES_TABLE_NAME + " (" +
                    THEME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    THEME_TITLE + " TEXT NOT NULL);";
            db.execSQL(themeQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
