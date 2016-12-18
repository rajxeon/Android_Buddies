package com.webina.rajsekhar.whackamole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedBacks extends AppCompatActivity {

    String user_id,solution_id;
    EditText feedback;
    String feedbackText;
    Button submit;
    String serverUrl;
    AlertDialog.Builder builder;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> name_text = new ArrayList<String>();
    ArrayList<String> feedback_text = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        user_id = getIntent().getStringExtra("user_id");
        solution_id = getIntent().getStringExtra("solution_id");
        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/add_feedback";


        setContentView(R.layout.activity_feed_backs);
        get_name_feedback_array();




        feedback=(EditText)findViewById(R.id.feedback);
        submit=(Button)findViewById(R.id.submitFeedback);
        builder=new AlertDialog.Builder(FeedBacks.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackText=feedback.getText().toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("test",response);

                                if(response.equals("0")){
                                    builder.setTitle("Input error");
                                    builder.setMessage("Please fill all the fields");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Email.setText("");
                                            //Password.setText("");
                                        }
                                    });
                                    AlertDialog alertDialog=builder.create();
                                    alertDialog.show();
                                }


                                else if(response.equals("1")){
                                    //Log.i("test",response);

                                    Toast.makeText(FeedBacks.this,"Solution added successfully ",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),FeedBacks.class);
                                    i.putExtra("user_id",user_id);
                                    i.putExtra("solution_id",solution_id);
                                    startActivity(i);
                                    //the response contains the insert id

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FeedBacks.this,"Error..",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("solution_id",solution_id);
                        params.put("feedback",feedbackText);
                        params.put("user_id",user_id);
                        return params;
                    }
                };

                MySingleton.getmInstance(FeedBacks.this).addToRequestqueue(stringRequest);
            }
        });



       //
    }

    public void get_name_feedback_array(){

        String serverUrlGetFeedBack=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_feedback";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrlGetFeedBack,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jArray;

                        try {
                            jArray  = new JSONArray(response);
                            int count=0;
                            String fdtx,fdname;

                            while (count<jArray.length()){
                                JSONObject JO=jArray.getJSONObject(count);
                                fdtx=JO.getString("feedback");
                                fdname=JO.getString("nickName");


                                name_text.add(fdname);
                                feedback_text.add(fdtx);

                                count++;


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
                        String[] nameArray=name_text.toArray(new String[name_text.size()]);
                        String[] feedbackArray=feedback_text.toArray(new String[feedback_text.size()]);

                        adapter=new RecyclerAdapter(nameArray,feedbackArray);
                        layoutManager=new LinearLayoutManager(FeedBacks.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FeedBacks.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("solution_id",solution_id);
                return params;
            }
        };

        MySingleton.getmInstance(FeedBacks.this).addToRequestqueue(stringRequest);
    }




}



