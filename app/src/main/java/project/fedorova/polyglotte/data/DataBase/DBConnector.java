package project.fedorova.polyglotte.data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import project.fedorova.polyglotte.data.Phrase;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class DBConnector {

    private static final String DATABASE_NAME = "polyglotte_new__database_";
    private static final int DATABASE_VERSION = 1;
    
    private static final String WORDS_TABLE_NAME = "words";

    private static final String WORD_ID = "_id";
    private static final String WORD_TITLE = "title";
    private static final String WORD_MAIN_TRANSLATION = "mainTranslation";
    private static final String WORD_TRANSLATIONS = "translations";
    private static final String WORD_THEMES = "theme";
    private static final String WORD_EXAMPLES = "examples";

    public static final int NUM_WORD_ID = 0;
    public static final int NUM_WORD_TITLE = 1;
    public static final int NUM_WORD_MAIN_TRANSLATION = 2;
    public static final int NUM_WORD_TRANSLATIONS = 3;
    public static final int NUM_WORD_THEMES = 4;
    public static final int NUM_WORD_EXAMPLES = 5;

    private static final String[] WORDS_COLUMNS = new String[] {WORD_ID, WORD_TITLE, WORD_MAIN_TRANSLATION, WORD_TRANSLATIONS, WORD_THEMES, WORD_EXAMPLES};

    private static final String THEMES_TABLE_NAME = "themes";

    private static final String THEME_ID = "_id";
    private static final String THEME_TITLE = "title";

    public static final int NUM_THEME_ID = 0;
    public static final int NUM_THEME_TITLE = 1;

    private static final String PHRASES_TABLE_NAME = "phrases";

    private static final String PHRASE_ID = "_id";
    private static final String PHRASE_TITLE = "title";
    private static final String PHRASE_TRANSLATION = "translation";
    private static final String PHRASE_THEME = "theme";

    public static final int NUM_PHRASE_ID = 0;
    public static final int NUM_PHRASE_TITLE = 1;
    public static final int NUM_PHRASE_TRANSLATION = 2;
    public static final int NUM_PHRASE_THEME = 3;

    private static final String[] PHRASES_COLUMNS = new String[] {PHRASE_ID, PHRASE_TITLE, PHRASE_TRANSLATION};

    private static final String PHRASE_THEMES_TABLE_NAME = "phrase_themes";

    private static final String PHRASE_THEME_ID = "_id";
    private static final String PHRASE_THEME_TITLE = "title";

    public static final int NUM_PHRASE_THEME_ID = 0;
    public static final int NUM_PHRASE_THEME_TITLE = 1;

    private OpenHelper dbHelper;

    public DBConnector(Context context, String langFrom, String langTo) {
        dbHelper = new OpenHelper(context, langFrom, langTo);
    }

    public void insertWord(Word word) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            ContentValues cv = new ContentValues();
            cv.put(WORD_ID, word.getID().toString());
            cv.put(WORD_TITLE, word.getWord());
            cv.put(WORD_MAIN_TRANSLATION, word.getMainTranslation());
            if (word.getTranslations() != null) {
                cv.put(WORD_TRANSLATIONS, readWriteManager.convertSetToString(word.getTranslations()));
            }
            if (word.getThemes() != null) {
                cv.put(WORD_THEMES, readWriteManager.convertSetToString(word.getThemes()));
            }
            if (word.getExamples() != null) {
                cv.put(WORD_EXAMPLES, readWriteManager.convertSetToString(word.getExamples()));
            }
            database.insertOrThrow(WORDS_TABLE_NAME, null, cv);
            if (word.getThemes() != null) {
                for (String theme : word.getThemes()) {
                    ContentValues cvTheme = new ContentValues();
                    cvTheme.put(THEME_ID, word.getID().toString());
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
            cv.put(WORD_MAIN_TRANSLATION, word.getMainTranslation());
            if (word.getTranslations() != null) {
                cv.put(WORD_TRANSLATIONS, readWriteManager.convertSetToString(word.getTranslations()));
            }
            if (word.getThemes() != null) {
                cv.put(WORD_THEMES, readWriteManager.convertSetToString(word.getThemes()));
            }
            if (word.getExamples() != null) {
                cv.put(WORD_EXAMPLES, readWriteManager.convertSetToString(word.getExamples()));
            }
            database.update(WORDS_TABLE_NAME, cv, WORD_ID + " = ?", new String[]{String.valueOf(word.getID())});
            database.delete(THEMES_TABLE_NAME, THEME_ID + " = ?", new String[]{String.valueOf(word.getID())});
            if (word.getThemes() != null) {
                for (String theme : word.getThemes()) {
                    ContentValues cvThemes = new ContentValues();
                    cvThemes.put(THEME_ID, word.getID().toString());
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

    public void delete(UUID id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(WORDS_TABLE_NAME, WORD_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public Word getWord(UUID id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        Cursor cursor = database.query(WORDS_TABLE_NAME, WORDS_COLUMNS, WORD_ID + " = ?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            cursor.moveToFirst();
            String word = cursor.getString(NUM_WORD_TITLE);
            String mainTranslation = cursor.getString(NUM_WORD_MAIN_TRANSLATION);
            Set<String> translations = readWriteManager.convertStringToSet(cursor.getString(NUM_WORD_TRANSLATIONS));
            Set<String> themes = readWriteManager.convertStringToSet(cursor.getString(NUM_WORD_THEMES));
            Set<String> examples = readWriteManager.convertStringToSet(cursor.getString(NUM_WORD_EXAMPLES));
            cursor.close();
            database.setTransactionSuccessful();
            database.endTransaction();
            return new Word(id, word, mainTranslation, translations, themes, examples);
        } else {
            return null;
        }
    }

    public Cursor getAllWords() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            return database.query(WORDS_TABLE_NAME, WORDS_COLUMNS, null, null, null, null, WORD_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            database.endTransaction();
        }
    }

    public void insertPhrase(Phrase phrase) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
            ContentValues cv = new ContentValues();
            cv.put(PHRASE_ID, phrase.getId().toString());
            cv.put(PHRASE_TITLE, phrase.getPhrase());
            cv.put(PHRASE_TRANSLATION, phrase.getTranslation());
            database.insertOrThrow(PHRASES_TABLE_NAME, null, cv);
            if (phrase.getTheme() != null) {
                ContentValues cvTheme = new ContentValues();
                cvTheme.put(PHRASE_THEME_ID, phrase.getId().toString());
                cvTheme.put(PHRASE_THEME_TITLE, phrase.getTheme());
                database.insertOrThrow(PHRASE_THEMES_TABLE_NAME, null, cvTheme);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public Cursor getAllPhrases() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            return database.query(PHRASES_TABLE_NAME, PHRASES_COLUMNS, null, null, null, null, PHRASE_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            database.endTransaction();
        }
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

    public Cursor getPhrasesByTheme(String theme) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] themes = {theme};
        String selection = PHRASE_THEMES_TABLE_NAME + "." + PHRASE_THEME_TITLE + " = ?";
        return database.query(PHRASES_TABLE_NAME + "inner join " +
                        PHRASE_THEMES_TABLE_NAME + " on " +
                        PHRASES_TABLE_NAME + "." + " = " +
                        PHRASE_THEMES_TABLE_NAME + "." + THEME_ID,
                PHRASES_COLUMNS, selection, themes, PHRASE_ID,
                "count(" + PHRASE_THEMES_TABLE_NAME + "." + PHRASE_THEME_TITLE + ") = " +
                        String.valueOf(1), null);
    }

    public Set<String> getThemesByWord(UUID id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(THEMES_TABLE_NAME, new String[]{THEME_TITLE}, THEME_ID + " = ?",
                new String[]{id.toString()}, null, null, null);
        Set<String> themes = new HashSet<>();
        while (cursor.moveToNext()) {
            themes.add(cursor.getString(NUM_THEME_TITLE));
        }
        cursor.close();
        return themes;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context, String langFrom, String langTo) {
            super(context, DATABASE_NAME + langFrom + langTo, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String wordsQuery = "CREATE TABLE " + WORDS_TABLE_NAME + " (" +
                    WORD_ID + " TEXT NOT NULL, " +
                    WORD_TITLE + " TEXT NOT NULL, " +
                    WORD_MAIN_TRANSLATION + " TEXT NOT NULL, " +
                    WORD_TRANSLATIONS + " TEXT, " +
                    WORD_THEMES + " TEXT, " +
                    WORD_EXAMPLES + " TEXT);";
            db.execSQL(wordsQuery);

            String themeQuery = "CREATE TABLE " + THEMES_TABLE_NAME + " (" +
                    THEME_ID + " TEXT NOT NULL, " +
                    THEME_TITLE + " TEXT NOT NULL);";
            db.execSQL(themeQuery);

            String phraseQuery = "CREATE TABLE " + PHRASES_TABLE_NAME + " (" +
                    PHRASE_ID + " TEXT NOT NULL, " +
                    PHRASE_TITLE + " TEXT NOT NULL, " +
                    PHRASE_TRANSLATION + " TEXT);";
            db.execSQL(phraseQuery);

            String phraseThemeQuery = "CREATE TABLE " + PHRASE_THEMES_TABLE_NAME + " (" +
                    PHRASE_THEME_ID + " TEXT NOT NULL, " +
                    PHRASE_THEME_TITLE + " TEXT NOT NULL);";
            db.execSQL(phraseThemeQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
