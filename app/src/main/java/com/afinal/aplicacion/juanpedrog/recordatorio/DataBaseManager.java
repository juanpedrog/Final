package com.afinal.aplicacion.juanpedrog.recordatorio;

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

    public static final String CREATE_TABLE="create table "+TABLE_NAME+"("+CN_ID+" integer primary key autoincrement,"+
            CN_NOMBRE+" text not null,"+
            CN_ARTICULO+" text not null," +
            CN_FECHA_PRESTAMO+" text not null, "+
            CN_FECHA_DEVOLUCION+" text not null"+");";
}
