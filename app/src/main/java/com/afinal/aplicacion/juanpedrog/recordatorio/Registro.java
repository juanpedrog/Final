package com.afinal.aplicacion.juanpedrog.recordatorio;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    Button btnAgregar;
    DatePicker fecha;
    String[] registro;
    EditText nombre,articulo,descripcion;
    Calendar calendar;
    String[] arr;
    boolean kill=false;
    TextView lblnom,lblart,lbldesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registro=new String[6];
        calendar=Calendar.getInstance();
        nombre=(EditText) findViewById(R.id.txtNombre);
        articulo=(EditText)findViewById(R.id.txtArticulo);
        descripcion=(EditText)findViewById(R.id.txtDescripcion);
        fecha=(DatePicker) findViewById(R.id.dateFecha);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        lblnom=(TextView)findViewById(R.id.lblNomR);
        lblart=(TextView)findViewById(R.id.lblArticuloR);
        lbldesc=(TextView)findViewById(R.id.lblDescripcionR);
    }
    public String sacarFechaActual(){
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
    public String validar(String fecHoy,String fecDev){
        String[] arrH=fecHoy.split("/");
        String[] arrD=fecDev.split("/");
        int[] numH=new int[arrH.length];
        int[] numD=new int[arrD.length];
        for(int i=0;i<arrH.length;i++){
            numH[i]=Integer.parseInt(arrH[i]);
            numD[i]=Integer.parseInt(arrD[i]);
        }
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
    public boolean validarDatePicker(String fecHoy,String fecDev){
        String[] arrH=fecHoy.split("/");
        String[] arrD=fecDev.split("/");
        int[] numH=new int[arrH.length];
        int[] numD=new int[arrD.length];
        for(int i=0;i<arrH.length;i++){
            numH[i]=Integer.parseInt(arrH[i]);
            numD[i]=Integer.parseInt(arrD[i]);
        }
        if(numH[2]==numD[2]){
            if(numH[1]==numD[1]){
                if(numH[0]==numD[0]){
                    return true;
                }else{
                    if(numH[0]>numD[0]){
                        return false;
                    }else{
                        return true;
                    }
                }
            }else{
                if(numH[1]>numD[1]){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            if(numH[2]>numD[2]){
                return false;
            }else{
                return true;
            }
        }
    }
    public void agregar(EditText nom,EditText art,EditText desc, DatePicker fec){
            registro[0] = nom.getText().toString();
            registro[1] = art.getText().toString();
            registro[2] = desc.getText().toString();
            registro[3] = calendar.getTime().toString();
            registro[4] = fec.getDayOfMonth() + "/" + (fec.getMonth() + 1) + "/" + fec.getYear();
            registro[5] = validar(sacarFechaActual(), registro[4]);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                if(nombre.getText().toString().equals("")||articulo.getText().toString().equals("")||
                        descripcion.getText().toString().equals("")||!validarDatePicker(sacarFechaActual(),
                    fecha.getDayOfMonth() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getYear())){
                    kill=false;
                }else{
                    kill=true;
                }
                if (kill) {
                    Intent intent = new Intent(Registro.this, Consulta.class);
                    agregar(nombre, articulo, descripcion, fecha);
                    Toast.makeText(Registro.this, arr[1] + "," + arr[2] + "," + arr[5], Toast.LENGTH_LONG).show();
                    intent.putExtra("Registro", registro);
                    Toast.makeText(Registro.this, "Agregado " + nombre.getText().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{

                    if(nombre.getText().toString().equals("")){
                        lblnom.setTextColor(Color.RED);
                    }
                    if(articulo.getText().toString().equals("")){
                        lblart.setTextColor(Color.RED);
                    }
                    if(descripcion.getText().toString().equals("")){
                        lbldesc.setTextColor(Color.RED);
                    }
                    if(!validarDatePicker(sacarFechaActual(),
                            fecha.getDayOfMonth() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getYear())){
                        Toast.makeText(this,"¡Fecha no valida!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,"¡Hay datos vacios!",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
