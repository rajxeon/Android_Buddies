package com.webina.rajsekhar.whackamole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Guide extends AppCompatActivity {
    String serverUrl;
    TextView guideTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_guide";
        guideTextView=(TextView)findViewById(R.id.guide_text);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            //Log.i("test",response);
                            //the response contains the insert id
                        try {
                            JSONObject jObject = new JSONObject(response);
                            guideTextView.setText((String) jObject.get("guide"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Guide.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("nickname","test");
                return params;
            }
        };

        MySingleton.getmInstance(Guide.this).addToRequestqueue(stringRequest);

    }
}
