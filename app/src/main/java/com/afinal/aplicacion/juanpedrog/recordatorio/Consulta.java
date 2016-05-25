package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class Consulta extends AppCompatActivity implements AdapterView.OnItemClickListener,SearchView.OnQueryTextListener{

    public DataBaseManager basedatos;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView lista;
    String[] registros;
    Calendar calendar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        //Variables
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        calendar = Calendar.getInstance();
        basedatos = new DataBaseManager(this);
        Intent intentRegistros = getIntent();
        Bundle datos = intentRegistros.getExtras();
        lista = (ListView) findViewById(R.id.lista);
        //Verifica si hay extras
        if (datos == null) {

        } else {
            registros = datos.getStringArray("Registro");
            basedatos.insertar(registros);
        }

        llenarTabla();
        //Actualiza la base de datos con la fecha de hoy
        actualizar();
        //Llena el listView
        registerForContextMenu(lista);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);

        return  super.onCreateOptionsMenu(menu);
    }
    public void llenarTabla() {
        //Saca los datos de la base de datos y los inserta en un ListView
        String[] from = new String[]{basedatos.CN_NOMBRE, basedatos.CN_ARTICULO, basedatos.CN_DESCRIPCION,
                basedatos.CN_FECHA_PRESTAMO, basedatos.CN_FECHA_DEVOLUCION, basedatos.CN_DISPONIBLE};
        int[] to = new int[]{R.id.Nom, R.id.Art, R.id.Desc, R.id.Fecha_Re, R.id.Fecha_Dev, R.id.Dispon};
        cursor = basedatos.crearCursorDatos();
        adapter = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to);
        lista.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        View viewNom=info.targetView;
        TextView nom=(TextView)viewNom.findViewById(R.id.Fecha_Re);
        switch (item.getItemId()) {
            case R.id.menuEliminar:
                basedatos.eliminar(nom.getText().toString());
                llenarTabla();
                actualizar();
                break;
            case R.id.menuCambiarEstado:
                String ar[]=new String[]{
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),
                        cursor.getString(5),"Devuelto"
                };
                basedatos.modificar(ar);
                llenarTabla();
                actualizar();
        }
        return super.onContextItemSelected(item);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Consulta Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.afinal.aplicacion.juanpedrog.recordatorio/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    public String validar(String fecHoy, String fecDev) {
        //Crea un array para la fecha [dia,mes,año]
        String[] arrH = fecHoy.split("/");
        String[] arrD = fecDev.split("/");
        int[] numH = new int[arrH.length];
        int[] numD = new int[arrD.length];
        for (int i = 0; i < arrH.length; i++) {
            numH[i] = Integer.parseInt(arrH[i]);
            numD[i] = Integer.parseInt(arrD[i]);
        }
        //Comparamos fechas para determinar el estado del registro
        if (numH[2] == numD[2]) {
            if (numH[1] == numD[1]) {
                if (numH[0] == numD[0]) {
                    return "Entregar hoy";
                } else {
                    if (numH[0] > numD[0]) {
                        return "Retrasado";
                    } else {
                        return "Pendiente";
                    }
                }
            } else {
                if (numH[1] > numD[1]) {
                    return "Retrasado";
                } else {
                    return "Pendiente";
                }
            }
        } else {
            if (numH[2] > numD[2]) {
                return "Retrasado";
            } else {
                return "Pendiente";
            }
        }
    }

    public String sacarFechaActual() {
        String[] arr;
        arr = calendar.getTime().toString().split(" ");
        switch (arr[1]) {
            case "Ene":
                arr[1] = "1";
                break;
            case "Feb":
                arr[1] = "2";
                break;
            case "Mar":
                arr[1] = "3";
                break;
            case "Apr":
                arr[1] = "4";
                break;
            case "May":
                arr[1] = "5";
                break;
            case "Jun":
                arr[1] = "6";
                break;
            case "Jul":
                arr[1] = "7";
                break;
            case "Ago":
                arr[1] = "8";
                break;
            case "Sep":
                arr[1] = "9";
                break;
            case "Oct":
                arr[1] = "10";
                break;
            case "Nov":
                arr[1] = "11";
                break;
            case "Dic":
                arr[1] = "12";
                break;
        }
        return arr[2] + "/" + arr[1] + "/" + arr[5];
    }

    public void actualizar() {
        cursor.moveToFirst();
        String[] arr = new String[6];
        try {
            do {
                //Toma cada uno de los valores de la base de datos
                arr[0] = cursor.getString(1);
                arr[1] = cursor.getString(2);
                arr[2] = cursor.getString(3);
                arr[3] = cursor.getString(4);
                arr[4] = cursor.getString(5);
                //Verifica si la fecha de devolucion con la fecha actual
                if(!cursor.getString(6).equals("Devuelto")){
                    arr[5] = validar(sacarFechaActual(), cursor.getString(5));
                }else{
                    arr[5]="Devuelto";
                }
                basedatos.modificar(arr);
            } while (cursor.moveToNext());
        }catch (CursorIndexOutOfBoundsException e){
            Toast.makeText(this, "No hay registros para mostrar", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Consulta Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.afinal.aplicacion.juanpedrog.recordatorio/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        boolean listo=false;
        cursor.moveToFirst();
        do{
           if(cursor.getString(1).equals(query)){
               Toast.makeText(this,"Encontrado",Toast.LENGTH_LONG).show();
           }
        }while(!listo);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
