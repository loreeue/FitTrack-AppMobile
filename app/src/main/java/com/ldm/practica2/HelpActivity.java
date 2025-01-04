package com.ldm.practica2;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button btnVolverHelp = findViewById(R.id.btnVolverHelp);
        btnVolverHelp.setOnClickListener(v -> {
            MediaPlayerSingleton.getInstance().stop();
            finish();
        });
    }
}