package com.example.exthreads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button btnProcessar;
    private TextView txtStatus;
    private ProgressBar pgbProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProcessar = findViewById(R.id.btnProcessar);
        txtStatus = findViewById(R.id.txtStatus);
        pgbProgresso = findViewById(R.id.pgbProgresso);

        btnProcessar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            txtStatus.setText(R.string.processando);
            btnProcessar.setEnabled(false);
            pgbProgresso.setVisibility(View.VISIBLE);
            pgbProgresso.setMax(15);
            pgbProgresso.setProgress(0);
            executarAlgoDemorado();

    }

    public void executarAlgoDemorado(){
        new Thread(new Runnable() {
            int progresso = 0;
            @Override
            public void run() {
                while (progresso < pgbProgresso.getMax()){
                    SystemClock.sleep(1000);
                    progresso++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pgbProgresso.setProgress(progresso);
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pgbProgresso.setVisibility(View.INVISIBLE);
                        btnProcessar.setEnabled(true);
                        txtStatus.setText(R.string.finalizado);
                    }
                });
            }
        }).start();
    }

}