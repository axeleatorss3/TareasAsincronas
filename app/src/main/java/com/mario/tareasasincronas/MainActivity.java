package com.mario.tareasasincronas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button button,button2;
ProgressBar vida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        vida = findViewById(R.id.progressBar);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EjemploAynscTask ejemploAynscTask = new EjemploAynscTask();
                ejemploAynscTask.execute();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button:
                        for(int i=1; i<=10;i++){
                            Unsegundo();
                        }
                        break;
                    case R.id.button2:
                        EjemploAynscTask ejemploAynscTask = new EjemploAynscTask();
                        ejemploAynscTask.execute();

                }
            }
        });

    }

    private void Unsegundo(){
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }
    }
    private class EjemploAynscTask extends AsyncTask<Void,Integer,Boolean>{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        //entrada variables y bojetos(lo que se hace antes)
        protected void onPreExecute() {
            super.onPreExecute();
            vida.setMax(100);
            vida.setProgress(100);
        }
        //tareas en segundo plano que se comunican con el primer hilo por el metodo publiprogress (lo que se hace en el segundo hilo que se pueda comunicar con el hilo principal con publiprogress)

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i=1; i<=10; i++){
                Unsegundo();
                publishProgress(i*-10+100);
                if(isCancelled())
                    break;
            }
            return true;
        }
        //dependiendo del porcentaje de la tarea en segundo plano aqui es donde de acuerdo al progreso de la tarea podemos cambiar componentes
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            vida.setProgress(values[0]);
        }
        //la salida(lo que dice cuando se termina el hilo)
        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);
            if(resultado){
                Toast.makeText(MainActivity.this, "tarea finalizada en AsyncTask", Toast.LENGTH_SHORT).show();
            }
        }
        //cuando se corta las instrucciones de uin hilo, aqui se guarda lo de bases de datos
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(MainActivity.this, "tarea cancelada en AsyncTask", Toast.LENGTH_SHORT).show();
        }

    }
}
