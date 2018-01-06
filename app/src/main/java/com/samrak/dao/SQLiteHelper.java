package com.samrak.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samet on 3.2.2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper dataBaseHelper;

    static final String KEY_ID = "keyId";
    static final String KEY_NAME = "keyName";
    static final String KEY_SURNAME = "keySurname";
    static final String KEY_DATA = "keyData";
    static final String KEY_INT = "keyInt";
    static final String KEY_FLOAT = "keyFloat";


    static final String DATABASE = "activate";
    static final String TABLE = "person";
    static final int DATABASE_VERSION = 1;

    static String createTable = "CREATE TABLE " + TABLE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " VARCHAR(10), "
            + KEY_SURNAME + " NVARCHAR(15), "
            + KEY_DATA + " TEXT, "
            + KEY_INT + " INTEGER, "
            + KEY_FLOAT + " FLOAT  )";

    static String createTable2 = "CREATE TABLE " + "material" + " ( " + "materialId" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "materialName" + " VARCHAR(10), "
            + "materialSurname" + " NVARCHAR(15), "
            + "materialData" + " TEXT  )";


    public SQLiteHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + createTable);
        db.execSQL("DROP TABLE IF EXISTS " + createTable2);
        onCreate(db);
    }

    public static SQLiteHelper getInstance(Context context) {
        return dataBaseHelper = (dataBaseHelper == null) ? new SQLiteHelper(context) : dataBaseHelper;
    }
}
