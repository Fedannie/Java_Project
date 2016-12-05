package project.fedorova.polyglotte.data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.stream.Stream;

import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class DBConnector {

    private static final String DATABASE_NAME = "polyglotte.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TRANSLATIONS = "translations";
    private static final String COLUMN_THEMES = "theme";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;
    private static final int NUM_COLUMN_TRANSLATIONS = 2;
    private static final int NUM_COLUMN_THEMES = 3;

    private SQLiteDatabase wordDB;

    public DBConnector(Context context) {
        OpenHelper dbOpenHelper = new OpenHelper(context);
        wordDB = dbOpenHelper.getWritableDatabase();
    }

    public long insert(Word word) {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, word.getWord());
        cv.put(COLUMN_TRANSLATIONS, readWriteManager.convertArrayToString(word.getTranslations()));
        cv.put(COLUMN_THEMES, readWriteManager.convertArrayToString(word.getThemes()));
        return wordDB.insert(TABLE_NAME, null, cv);
    }

    public int update(Word word) {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, word.getWord());
        cv.put(COLUMN_TRANSLATIONS, readWriteManager.convertArrayToString(word.getTranslations()));
        cv.put(COLUMN_THEMES, readWriteManager.convertArrayToString(word.getThemes()));
        return wordDB.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[] {String.valueOf(word.getID())});
    }

    public int deleteAll() {
        return wordDB.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        wordDB.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public Word select(long id) {
        Cursor cursor = wordDB.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[] {String.valueOf(id)}, null, null, COLUMN_TITLE);
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        cursor.moveToFirst();
        String word = cursor.getString(NUM_COLUMN_TITLE);
        String translations = cursor.getString(NUM_COLUMN_TRANSLATIONS);
        String themes = cursor.getString(NUM_COLUMN_THEMES);
        int size = readWriteManager.convertStringToArray(translations).length;
        String[] all = readWriteManager.convertStringToArray(translations + "," + themes);
        cursor.close();
        return new Word(id, word, size, all);
    }

    public ArrayList<Word> selectAll() {
        Cursor cursor = wordDB.query(TABLE_NAME, null, null, null, null, null, COLUMN_TITLE);

        ArrayList<Word> words = new ArrayList<Word>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
                long id = cursor.getLong(NUM_COLUMN_ID);
                String word = cursor.getString(NUM_COLUMN_TITLE);
                String translations = cursor.getString(NUM_COLUMN_TRANSLATIONS);
                String themes = cursor.getString(NUM_COLUMN_THEMES);
                int size = readWriteManager.convertStringToArray(translations).length;
                String[] all = readWriteManager.convertStringToArray(translations + "," + themes);
                words.add(new Word(id, word, size, all));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return words;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TRANSLATIONS + " TEXT, " +
                    COLUMN_THEMES + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
