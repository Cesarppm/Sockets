package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultado;
    Button btn1;
    EditText etc1, etc2,ethipo,etip, etp;
    String C1,C2,HIPO,ip,puerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado=(TextView) findViewById(R.id.respuesta);
        btn1=(Button) findViewById(R.id.btnconsul);
        btn1.setOnClickListener(this);
        etc1=(EditText) findViewById(R.id.editTextC1);
        etc2=(EditText) findViewById(R.id.editTextC2);
        ethipo=(EditText)findViewById(R.id.editTexhipo);
        etip=(EditText) findViewById(R.id.editTextIp);
        etp=(EditText) findViewById(R.id.editTextPuerto);
    }
    public String ejecutaCliente(String nombre,String ipserv,int puerto) {
        String registroeventos="Conectando al socket " + ipserv + ":" + puerto+"\n";
        try {
            Socket sk = new Socket(ipserv,puerto);
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(sk.getInputStream()));
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()),true);
            registroeventos+="Conexion establecida !!!\n";
            registroeventos+="enviando al servidor la palabra -->"+nombre+"\n";
            salida.println(nombre);
            registroeventos+="recibiendo del servidor...\n";
            registroeventos+=entrada.readLine()+"\n";
            sk.close();
        }
        catch (Exception e) {
            registroeventos="Error !!\n " + e.toString()+"\n";
        }
        return registroeventos;
    } //ejecutaCliente



    public class Tarea extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return ejecutaCliente(strings[0],strings[1],Integer.parseInt(strings[2]));
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            resultado.setText(s);
        }
    }
    @Override
    public void onClick(View v) {
        String Cateto= "";
        if (v.getId()==R.id.btnconsul){
            C1= etc1.getText().toString().trim();
            C2= etc2.getText().toString().trim();
            HIPO= ethipo.getText().toString().trim();
            ip= etip.getText().toString().trim();
            puerto= etp.getText().toString().trim();
            Tarea T=new Tarea();
            Cateto = C1 + ":"+ C2+ ":"+HIPO;
            T.execute(Cateto,ip,puerto);
        }
    }

}