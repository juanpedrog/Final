package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    Button btnAgregar;
    DatePicker fecha;
    String[] registro;
    EditText nombre,articulo;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registro=new String[4];
        calendar=Calendar.getInstance();
        nombre=(EditText) findViewById(R.id.txtNombre);
        articulo=(EditText)findViewById(R.id.txtArticulo);
        fecha=(DatePicker) findViewById(R.id.dateFecha);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
    }
    public void agregar(EditText nom,EditText art, DatePicker fec){
        registro[0]=nom.getText().toString();
        registro[1]=art.getText().toString();
        registro[3]=fec.getDayOfMonth()+"/"+(fec.getMonth()+1)+"/"+fec.getYear();
        registro[2]=calendar.getTime().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                Intent intent= new Intent(Registro.this,MainActivity.class);
                agregar(nombre,articulo,fecha);
                intent.putExtra("Registro",registro);
                Toast.makeText(Registro.this,"Agregado "+nombre.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
}
