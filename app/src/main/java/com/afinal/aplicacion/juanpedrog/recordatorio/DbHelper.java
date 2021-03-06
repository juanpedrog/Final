package com.afinal.aplicacion.juanpedrog.recordatorio;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juanpedrog on 14/05/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="registro.sqlite";
    public static final int DB_SCHEME_VERSION=4;

    public DbHelper(Context context) {
        super(context, DB_NAME,null,DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS prestamos");
        db.execSQL(DataBaseManager.CREATE_TABLE);
    }
}
