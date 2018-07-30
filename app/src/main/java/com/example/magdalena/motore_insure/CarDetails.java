package com.example.magdalena.motore_insure;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;


public class CarDetails extends AppCompatActivity {

    public Button register;

    EditText holder,reg,policy,sdate,edate,gross,seating;

    EditText company;
    EditText Vehicle_make;
    AlertDialog.Builder builder;

    String Url = "http://192.168.43.233/database/cardetails.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        holder=(EditText) findViewById(R.id.editText12);
        reg=(EditText) findViewById(R.id.editText15);
        policy=(EditText) findViewById(R.id.editText13);
        sdate=(EditText) findViewById(R.id. editText20);
        edate=(EditText) findViewById(R.id. editText21);
        gross=(EditText) findViewById(R.id.editText16 );
       seating=(EditText) findViewById(R.id. editText18);
       register=(Button) findViewById(R.id.button);
       company=(EditText)findViewById(R.id.compan);
       Vehicle_make=(EditText) findViewById(R.id.Vehicle_mak);
        builder = new AlertDialog.Builder(CarDetails.this);



       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String Holder=holder.getText().toString().trim();
               final String RegistrationNumber = reg.getText().toString().trim();
               final String Policy = policy.getText().toString().trim();
               final String  StartDate = sdate.getText().toString().trim();
               final String EndDate = edate.getText().toString().trim();
               final String Company = company.getText().toString().trim();
               final String Gross = gross.getText().toString().trim();
               final String MAKE =Vehicle_make.getText().toString();
               final String SeatingCapacity = seating.getText().toString().trim();


               if (StartDate.equals("") ||Holder.equals("") || EndDate.equals("") ||Company.equals("") ||Gross.equals("")||SeatingCapacity.equals("") ||MAKE.equals("") ||RegistrationNumber.equals("") || Policy.equals("")) {
                   builder.setTitle("Something went wrong");
                   builder.setMessage("Fill in all the fields");
                   displayAlert("input error");
               } else {

                   StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONArray jsonArray = new JSONArray(response);
                               JSONObject jsonObject = jsonArray.getJSONObject(0);
                               String code = jsonObject.getString("code");
                               String message = jsonObject.getString("Message");
                               builder.setTitle("Server Response");
                               builder.setMessage(message);
                               displayAlert(code);

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                           //makeText(getApplicationContext(), "Successful " + response, Toast.LENGTH_LONG).show();

                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(getApplicationContext(), "Failed  " + error.getMessage(), Toast.LENGTH_LONG).show();
                           error.printStackTrace();
                       }
                   }) {


                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {

                           Map<String, String> data = new HashMap<>();
                           data.put("placeholder", Holder);
                           data.put("policyNumber", Policy);
                           data.put("CarRegNo", RegistrationNumber);
                           data.put("VehicleMake", MAKE);
                           data.put("GrossWeight", Gross);
                           data.put("SeatingCapacity", SeatingCapacity);
                           data.put("StartDate", StartDate);
                           data.put("EndDate", EndDate);
                           data.put("Company", Company);



                           return data;
                       }
                   };
                   SingletonParternQueue.getInstance(CarDetails.this).addToRequestQue(request);
                   Intent i = new Intent(CarDetails.this, Confirm.class);
                   startActivity(i);

               }
           }

       });
    }


    private void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (code.equals("Input error")){
                    holder.setText("");
                    reg.setText("");
                    policy.setText("");
                    sdate.setText("");
                    edate.setText("");
                    gross.setText("");
                    seating.setText("");
                    company.setText("");
                    Vehicle_make.setText("");




                }
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}


