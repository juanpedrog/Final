package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    Button btnAgregar;
    DatePicker fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        fecha=(DatePicker) findViewById(R.id.dateFecha);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:

                Toast.makeText(Registro.this,fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
