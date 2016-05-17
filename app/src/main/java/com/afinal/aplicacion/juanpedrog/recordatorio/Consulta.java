package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Consulta extends AppCompatActivity {

    public DataBaseManager basedatos;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        basedatos=new DataBaseManager(this);
        Intent intentRegistros=getIntent();
        Bundle datos=intentRegistros.getExtras();
        lista=(ListView)findViewById(R.id.lista);
        if(datos==null){
            Toast.makeText(this,"Error al agregar",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this,arr[0]+","+arr[1]+","+arr[2]+","+arr[3],Toast.LENGTH_LONG).show();
            basedatos.insertar(datos.getStringArray("Registro"));
        }
        String[] from=new String[]{basedatos.CN_NOMBRE,basedatos.CN_ARTICULO,basedatos.CN_DESCRIPCION,
                basedatos.CN_FECHA_PRESTAMO,basedatos.CN_FECHA_DEVOLUCION,basedatos.CN_DISPONIBLE};
        int[] to=new int[]{R.id.Nom,R.id.Art,R.id.Desc,R.id.Fecha_Re,R.id.Fecha_Dev,R.id.Dispon};
        cursor=basedatos.crearCursorDatos();
        adapter=new SimpleCursorAdapter(this,R.layout.lista,cursor,from,to);
        lista.setAdapter(adapter);
    }
}
