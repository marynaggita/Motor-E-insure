package com.example.magdalena.motore_insure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PaymentActivity extends AppCompatActivity {

    Button airtel,Mtn;
    EditText regno,vehtype,compny,premium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        airtel=(Button)findViewById(R.id.airtel);
       Mtn=(Button)findViewById(R.id.mtn);
       regno=(EditText)findViewById(R.id.editText3);

        Spinner myspinner =(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(PaymentActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.company));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdapter);




            Spinner spinner=(Spinner)findViewById(R.id.sweet) ;
        ArrayAdapter<String> m = new ArrayAdapter<>(PaymentActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Vehicle_make));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(m);




        airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,payment2Activity.class);
                startActivity(intent);
            }
        });
        Mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,payment2Activity.class);
                startActivity(intent);

            }
        });

    }
}
