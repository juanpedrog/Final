package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TableLayout;

/**
 * Created by juanpedrog on 14/05/16.
 */
public class DataBaseManager {
    public static final String TABLE_NAME="prestamos";
    public static final String CN_ID="id";
    public static final String CN_NOMBRE="nombre";
    public static final String CN_ARTICULO="articulo";
    public static final String CN_FECHA_PRESTAMO="fecha_prestamo";
    public static final String CN_FECHA_DEVOLUCION="fecha_devolucion";
    public static final String CN_DISPONIBLE="disponible";

    public static final String CREATE_TABLE="create table "+TABLE_NAME+"("+CN_ID+" integer primary key autoincrement,"+
            CN_NOMBRE+" text not null,"+
            CN_ARTICULO+" text not null," +
            CN_FECHA_PRESTAMO+" text not null, "+
            CN_FECHA_DEVOLUCION+" text not null"+");";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper=new DbHelper(context);
        db= helper.getWritableDatabase();
    }
    public ContentValues generadorContent(String[] registros){
        ContentValues valores=new ContentValues();
        valores.put(CN_NOMBRE,registros[0]);
        valores.put(CN_ARTICULO,registros[1]);
        valores.put(CN_FECHA_PRESTAMO,registros[2]);
        valores.put(CN_FECHA_DEVOLUCION,registros[3]);
        return valores;
    }
    public void insertar(String[] registros){
        db.insert(TABLE_NAME,null,generadorContent(registros));
    }
}
