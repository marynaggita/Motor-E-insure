package com.example.magdalena.motore_insure;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OcrActivity extends AppCompatActivity {

    TextView nam,polic,carRegN,mak,grossWeigh,sittingCapacit,startDat,endDat,insurance;



   CameraSource mCameraSource;
   Button request;
   TextView  mTextView;
   SurfaceView  mCameraView;
   final int RequestPermissionID =1001;
   String txt;
   EditText editText;
   String url = "http://192.168.43.233/database/fetchdata.php";



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         switch (requestCode){
             case RequestPermissionID:
             {
                 if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                 {
                     if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                         Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED);
                     return;
                 }
                 try {
                     mCameraSource.start(mCameraView.getHolder());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr2);

        nam = (TextView) findViewById(R.id.textView30);

        carRegN = (TextView) findViewById(R.id.textView47);
        mak = (TextView) findViewById(R.id.textView40);


        startDat = (TextView) findViewById(R.id.textView41);
        endDat = (TextView) findViewById(R.id.textView42);


        mTextView = (TextView) findViewById(R.id.text_view);
        editText = (EditText) findViewById(R.id.xx);
        mCameraView = (SurfaceView) findViewById(R.id.surfaceView);
        request = (Button) findViewById(R.id.btnrequest);

        //Create the TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("OcrActivity", "Detector dependencies not loaded yet");
        } else {

            //Initialize camerasource to use high resolution and set Autofocus on.
            mCameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();

            /**
             * Add call back to SurfaceView and check if camera permission is granted.
             * If permission is granted we can start our cameraSource and pass it to surfaceView
             */
            mCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(OcrActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestPermissionID);
                            return;
                        }
                        mCameraSource.start(mCameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                /**
                 * Release resources for cameraSource
                 */
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCameraSource.stop();
                }
            });

            //Set the TextRecognizer's Processor.
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                /**
                 * Detect all the text from camera using TextBlock and the values into a stringBuilder
                 * which will then be set to the textView.
                 */
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {

                        mTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                mTextView.setText(stringBuilder.toString());
                            }


                        });

                        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {

                                int start = mTextView.getSelectionStart();
                                int end = mTextView.getSelectionEnd();
                                if (start == -1 && end == -1) {
                                    return true;
                                }
                                String mSelectedText = ((TextView) view).getText().toString().substring(start, end);
                                System.out.println(mSelectedText);

                                return false;
                            }
                        });


                    }

                }
            });

            request.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    txt = editText.getText().toString().trim();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        nam.setText(response.getString("placeholder"));

                                        carRegN.setText(response.getString("CarRegNumber"));
                                        mak.setText(response.getString("VehicleMake"));


                                        startDat.setText(response.getString("StartDate"));
                                        endDat.setText(response.getString("EndDate"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OcrActivity.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("CarRegNumber", txt);

                            return params;
                        }
                    };
                    SingletonParternQueue.getInstance(OcrActivity.this).addToRequestQue(jsonObjectRequest);

                }
            });


        }
    }}




