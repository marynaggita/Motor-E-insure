package com.example.magdalena.motore_insure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment3Activity extends AppCompatActivity {
    Button confirm;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment3);

        confirm=(Button)findViewById(R.id.button7);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext()," payment Submitted and received",Toast.LENGTH_LONG).show();

            }
        });
    }
}
