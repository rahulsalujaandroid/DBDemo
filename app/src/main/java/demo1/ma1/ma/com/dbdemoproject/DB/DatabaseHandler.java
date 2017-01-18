package demo1.ma1.ma.com.dbdemoproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context mContext;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DictionaryDB";
    //  Table Name
    private static final String TABLE = "Dictionary";
    // Table Column names
    private static final String KEY_ID = "_id";
    public static final String WORD = "Word";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DICITIONARY_TABLE = "CREATE TABLE " + TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WORD + " TEXT" + ")";
        db.execSQL(CREATE_DICITIONARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // Adding Entry in DB
    public void addWords() {
        ArrayList<String> wordList = getWordList();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (String str : wordList) {
                values.put(WORD, str);
                db.insert(TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    private ArrayList<String> getWordList() {
        ArrayList<String> wordList = new ArrayList<>();
        String stringBuilder = "";
        try {
            InputStream inputStream = mContext.getAssets().open("keywords.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                stringBuilder = stringBuilder + str;
            }
            in.close();
        } catch (Exception e) {
        }

        if (!TextUtils.isEmpty(stringBuilder)) {
            ArrayList aList = new ArrayList(Arrays.asList(stringBuilder.split(",")));
            for (int i = 0; i < aList.size(); i++) {
                wordList.add(aList.get(i).toString());
            }
        }
        return wordList;
    }

    // Search From DB
    public Cursor getWords(String query) {
        ArrayList<String> wordList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " WHERE " + WORD + " LIKE " + "'" + query + "%" + "' LIMIT 10";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }
}
