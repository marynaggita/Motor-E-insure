package com.example.magdalena.motore_insure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class payment2Activity extends AppCompatActivity {
    EditText pin,phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);

    pin =(EditText)findViewById(R.id.editText6);
        phone =(EditText)findViewById(R.id.editText8);
        submit=(Button)findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment2Activity.this,Payment3Activity.class);
                startActivity(intent);
            }
        });


    }
}
