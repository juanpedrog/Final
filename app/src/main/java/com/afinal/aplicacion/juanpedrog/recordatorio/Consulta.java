package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.ContextMenu;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class Consulta extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public DataBaseManager basedatos;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView lista;
    String[] registros;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        //Variables
        calendar=Calendar.getInstance();
        basedatos=new DataBaseManager(this);
        Intent intentRegistros=getIntent();
        Bundle datos=intentRegistros.getExtras();
        lista=(ListView)findViewById(R.id.lista);
        lista.setOnItemClickListener(this);
        //Verifica si hay extras
        if(datos==null){

        }else{
            registros=datos.getStringArray("Registro");
            basedatos.insertar(registros);
        }
        //Saca los datos de la base de datos y los inserta en un ListView
        String[] from=new String[]{basedatos.CN_NOMBRE,basedatos.CN_ARTICULO,basedatos.CN_DESCRIPCION,
                basedatos.CN_FECHA_PRESTAMO,basedatos.CN_FECHA_DEVOLUCION,basedatos.CN_DISPONIBLE};
        int[] to=new int[]{R.id.Nom,R.id.Art,R.id.Desc,R.id.Fecha_Re,R.id.Fecha_Dev,R.id.Dispon};
        cursor=basedatos.crearCursorDatos();
        //Actualiza la base de datos con la fecha de hoy
        actualizar();
        //Llena el listView
        adapter=new SimpleCursorAdapter(this,R.layout.lista,cursor,from,to);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    public String validar(String fecHoy,String fecDev){
        //Crea un array para la fecha [dia,mes,año]
        String[] arrH=fecHoy.split("/");
        String[] arrD=fecDev.split("/");
        int[] numH=new int[arrH.length];
        int[] numD=new int[arrD.length];
        for(int i=0;i<arrH.length;i++){
            numH[i]=Integer.parseInt(arrH[i]);
            numD[i]=Integer.parseInt(arrD[i]);
        }
        //Comparamos fechas para determinar el estado del registro
        if(numH[2]==numD[2]){
            if(numH[1]==numD[1]){
                if(numH[0]==numD[0]){
                    return "Entregar hoy";
                }else{
                    if(numH[0]>numD[0]){
                        return "Retrasado";
                    }else{
                        return "Pendiente";
                    }
                }
            }else{
                if(numH[1]>numD[1]){
                    return "Retrasado";
                }else{
                    return "Pendiente";
                }
            }
        }else{
            if(numH[2]>numD[2]){
                return "Retrasado";
            }else{
                return"Pendiente";
            }
        }
    }
    public String sacarFechaActual(){
        String[] arr;
        arr=calendar.getTime().toString().split(" ");
        switch (arr[1]){
            case "Ene": arr[1]="1";break;
            case "Feb": arr[1]="2";break;
            case "Mar": arr[1]="3";break;
            case "Apr": arr[1]="4";break;
            case "May": arr[1]="5";break;
            case "Jun": arr[1]="6";break;
            case "Jul": arr[1]="7";break;
            case "Ago": arr[1]="8";break;
            case "Sep": arr[1]="9";break;
            case "Oct": arr[1]="10";break;
            case "Nov": arr[1]="11";break;
            case "Dic": arr[1]="12";break;
        }
        return arr[2]+"/"+arr[1]+"/"+arr[5];
    }
    public void actualizar(){
        cursor.moveToFirst();
        String[] arr=new String[6];
        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();
            do{
                //Toma cada uno de los valores de la base de datos
                arr[0]=cursor.getString(1);
                arr[1]=cursor.getString(2);
                arr[2]=cursor.getString(3);
                arr[3]=cursor.getString(4);
                arr[4]=cursor.getString(5);
                //Verifica si la fecha de devolucion con la fecha actual
                arr[5]=validar(sacarFechaActual(),cursor.getString(5));
                basedatos.modificar(arr);
            }while(cursor.moveToNext());
    }

    @Override
    public void onClick(View v) {
    }
}
