package com.example.magdalena.motore_insure;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup2 extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editName;
    private Button buttonSignup;
    private Button buttonSignin;
    AlertDialog.Builder builder;


    String Url = "http://192.168.43.233/database/insertingData.php";


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        editName = (EditText) findViewById(R.id.editText5);
        editTextPassword = (EditText) findViewById(R.id.editText1);
        editTextEmail = (EditText) findViewById(R.id.editText);
        buttonSignup = (Button) findViewById(R.id.signup);
        buttonSignin = (Button) findViewById(R.id.login);




        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(Signup2.this, Login.class);
                startActivity(t);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                final String name = editName.getText().toString().trim();

                if (name.equals("") || email.equals("") || password.equals("")) {
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
                            Intent i = new Intent(Signup2.this, CarDetails.class);
                            startActivity(i);

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
                            data.put("name", name);
                            data.put("email", email);
                            data.put("password", password);


                            return data;
                        }
                    };
                    SingletonParternQueue.getInstance(Signup2.this).addToRequestQue(request);


                }
            }

        });
    }


    private void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (code.equals("Input error")){
                    editTextPassword.setText("");

                }
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}


