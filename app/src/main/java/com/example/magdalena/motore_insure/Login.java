package com.example.magdalena.motore_insure;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {
    EditText Password;
    EditText email;
    Button Login;
    Button reg;
    AlertDialog.Builder builder;

    String url  =  "http://192.168.43.233/database/login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Password = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.user);
        Login = (Button) findViewById(R.id.log);
        reg = (Button) findViewById(R.id.reg);
        builder = new AlertDialog.Builder(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(com.example.magdalena.motore_insure.Login.this, SelectionActivity.class);
                startActivity(i);
            }
        });



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                final String password = Password.getText().toString();

                ///check validation
                if (TextUtils.isEmpty(email.getText().toString())) {
                   email.setError("Please enter your username");
                    email.requestFocus();

                    return;
                }

                if (TextUtils.isEmpty(Password.getText().toString())) {
                    Password.setError("Please enter your password");
                    Password.requestFocus();

                    return;
                }

                if (Email.equals("")||password.equals("")) {
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid username and password...");

                }else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if (code.equals("Login failed")){
                                    builder.setTitle("Login Error");
                                    displayAlert(jsonObject.getString("message"));
                                }else {
                                    Intent i = new Intent(Login.this,HomeActivity.class);

                                    startActivity(i);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this,"Error "+error.getMessage(),Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();



                            params.put("email", Email);
                            params.put("password", password);
                            return params;
                        }
                    };
                    SingletonParternQueue.getInstance(Login.this).addToRequestQue(stringRequest);
                }



            }
        }
        );
    }
    public void displayAlert(String message){
        builder.setMessage(message);
        builder
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                       email.setText("");
                        Password.setText("");
                    }
                });
AlertDialog alertDialog=builder.create();
alertDialog.show();
    }
}

