package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.DeniedByServerException;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Button btnRegistro;
    public DataBaseManager basedatos;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        btnRegistro=(Button) findViewById(R.id.nuevo);
        try{
            btnRegistro.setOnClickListener(this);
        }catch(NullPointerException e){
            System.out.println("NullPointerException");
        }
        basedatos=new DataBaseManager(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.afinal.aplicacion.juanpedrog.recordatorio/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        Intent intentRegistros=getIntent();
        Bundle datos=intentRegistros.getExtras();
        if(datos==null){
            Toast.makeText(this,"Error al agregar",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this,arr[0]+","+arr[1]+","+arr[2]+","+arr[3],Toast.LENGTH_LONG).show();
            basedatos.insertar(datos.getStringArray("Registro"));
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nuevo:
                Intent intent=new Intent(MainActivity.this,Registro.class);
                startActivity(intent);
                break;
        }
    }
}
