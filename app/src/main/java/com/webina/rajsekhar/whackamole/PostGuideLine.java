package com.webina.rajsekhar.whackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostGuideLine extends AppCompatActivity {
    Button submitGuideLine;
    EditText guideEditText;
    String  guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_guide_line);



        submitGuideLine=(Button)findViewById(R.id.submitGuideLine);
        guideEditText=(EditText)findViewById(R.id.guideEditText);


        getCurrentGuide();

        submitGuideLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guide=guideEditText.getText().toString();
                postGuideLine();
            }
        });
    }

    public void getCurrentGuide(){

        String serverUrlGetGuide=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_guide_text";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrlGetGuide,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        guideEditText.setText(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostGuideLine.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };

        MySingleton.getmInstance(PostGuideLine.this).addToRequestqueue(stringRequest);
    }

    public void postGuideLine(){
        if(guide.length()==0) {
            Toast.makeText(PostGuideLine.this, "Guide line can not be empty", Toast.LENGTH_LONG).show();
            return;


        }
            String serverUrlGetFeedBack=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/submit_guide";
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrlGetFeedBack,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                Toast.makeText(PostGuideLine.this,"Successfully updated",Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PostGuideLine.this,"Error..",Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("guide",guide);
                    return params;
                }
            };

            MySingleton.getmInstance(PostGuideLine.this).addToRequestqueue(stringRequest);
        }



    }

