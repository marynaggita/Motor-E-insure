package com.example.magdalena.motore_insure;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView name;


    Button pay,home,image,help,Notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name=(TextView)findViewById(R.id.textname);
        pay=(Button)findViewById(R.id.button4);
        home=(Button)findViewById(R.id.button3);
       image=(Button)findViewById(R.id.button5);
       help=(Button)findViewById(R.id.button6);
        Notify=(Button)findViewById(R.id.button8);




        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this,PaymentActivity.class);
                startActivity(i);

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this,PaymentActivity.class);
                startActivity(i);

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this,OcrActivity.class);
                startActivity(i);

            }
        });
       help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this,HelpActivity.class);
                startActivity(i);

            }
        });
       Notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //notificationcall();
            }
        });
    }

}
