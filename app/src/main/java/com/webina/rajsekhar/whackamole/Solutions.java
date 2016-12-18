package com.webina.rajsekhar.whackamole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class Solutions extends AppCompatActivity {

    String user_id;
    EditText _title,_body,searchSolution;
    AlertDialog.Builder builder;
    String serverUrl,serverUrlgetSolution;
    SolutionAdapter solutionAdapter;
    ListView listView;
    String keyword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = getIntent().getStringExtra("user_id");
        keyword = getIntent().getStringExtra("keyword");
        //Log.i("test",user_id);
        setContentView(R.layout.activity_solutions);

        solutionAdapter=new SolutionAdapter(this,R.layout.row_layout);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(solutionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SolutionsParser item = (SolutionsParser) solutionAdapter.getItem(i);

                Intent in =new Intent(Solutions.this,FeedBacks.class);
                in.putExtra("solution_id",item.get_id());
                in.putExtra("user_id",user_id);
                startActivity(in);

                //Log.i("test", String.valueOf(item.get_id()));

            }
        });

        Button submit_solution=(Button)findViewById(R.id.submit_solution);
        Button buttonSearch=(Button)findViewById(R.id.buttonSearch);

        _title=(EditText)findViewById(R.id.solution_title);
        _body=(EditText)findViewById(R.id.solutionBody);
        searchSolution=(EditText)findViewById(R.id.searchSolution);

        builder=new AlertDialog.Builder(Solutions.this);

        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/add_solution";
        serverUrlgetSolution=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_all_solution";



        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Solutions.class);
                i.putExtra("user_id",user_id);
                i.putExtra("keyword",searchSolution.getText().toString());
                startActivity(i);
            }
        });

        submit_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title,body;
                title   =_title.getText().toString();
                body   =_body.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


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

                                    Toast.makeText(Solutions.this,"Solution added successfully ",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),MainScreen.class);
                                    i.putExtra("user_id",user_id);
                                    startActivity(i);
                                    //the response contains the insert id

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Solutions.this,"Error..",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("title",title);
                        params.put("body",body);
                        params.put("user_id",user_id);
                        return params;
                    }
                };

                MySingleton.getmInstance(Solutions.this).addToRequestqueue(stringRequest);

            }
        });

        this.get_all_solutions(keyword);
    }

    public void get_all_solutions(final String keyword){

        //--------------------------------------------------------------------------------


        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrlgetSolution,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONArray  jArray;
                        try {
                            jArray  = new JSONArray(response);
                            int count=0;
                            String name,title,body,_id;



                            while (count<jArray.length()){
                                JSONObject JO=jArray.getJSONObject(count);
                                title=JO.getString("title");
                                name=JO.getString("nickName");
                                body=JO.getString("body");
                                _id=JO.getString("id");

                                //Log.i("test",JO.toString());

                                SolutionsParser solutionsParser=new SolutionsParser(name,title,body,_id);
                                solutionAdapter.add(solutionsParser);
                                count++;


                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Solutions.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                //params.put("title",title);
                //params.put("body",body);
                params.put("user_id",user_id);
                params.put("keyword",keyword);
                return params;
            }
        };

        MySingleton.getmInstance(Solutions.this).addToRequestqueue(stringRequest);
    }
}
