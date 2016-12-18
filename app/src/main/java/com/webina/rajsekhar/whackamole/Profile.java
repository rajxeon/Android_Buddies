package com.webina.rajsekhar.whackamole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    EditText nickname,firstname,lastname,gender,email,password;
    Button save,delete;
    String user_id;
    String serverUrl, serverEditUrl;;
    AlertDialog.Builder builder;
    TextView textViewTitle;
    String from_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_id    = getIntent().getStringExtra("user_id");
        from_admin = getIntent().getStringExtra("from_admin");

        //Log.i("test", String.valueOf(from_admin));

        setContentView(R.layout.activity_sign_in);
        nickname    =(EditText)findViewById(R.id.nickname_input);
        firstname   =(EditText)findViewById(R.id.firstname_input);
        lastname    =(EditText)findViewById(R.id.lastname_input);
        gender      =(EditText)findViewById(R.id.gender_input);
        email       =(EditText)findViewById(R.id.email_input);
        password    =(EditText)findViewById(R.id.password_input);
        textViewTitle=(TextView) findViewById(R.id.textViewTitle);

        save      =(Button) findViewById(R.id.submit);
        delete      =(Button) findViewById(R.id.delete);

        if(from_admin.equals("1")){
            delete.setVisibility(View.VISIBLE);
        }
        save.setText("Save");
        email.setEnabled(false);
        textViewTitle.setText("View or Edit profile");
        builder=new AlertDialog.Builder(getApplicationContext());

        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_profile_info";
        final String userId;
        userId   =String.valueOf(user_id);
        serverEditUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/update_profile_info";


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_user();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String _nickname,_firstname,_lastname,_gender,_email,_password;
                _nickname   =nickname.getText().toString();
                _firstname  =firstname.getText().toString();
                _lastname   =lastname.getText().toString();
                _gender     =gender.getText().toString();
                _email      =email.getText().toString();
                _password   =password.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, serverEditUrl,
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


                                else{

                                    Toast.makeText(Profile.this,"Successfully updated",Toast.LENGTH_SHORT).show();

                                    //the response contains the insert id

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Profile.this,"Error..",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("nickname",_nickname);
                        params.put("firstname",_firstname);
                        params.put("lastname",_lastname);
                        params.put("gender",_gender);
                        params.put("email",_email);
                        params.put("password",_password);
                        return params;
                    }
                };

                MySingleton.getmInstance(Profile.this).addToRequestqueue(stringRequest);

            }
        });




        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if(response.equals("0")){
                            //Duplicate email address
                            builder.setTitle("Error");
                            builder.setMessage("No data fetched");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(getApplicationContext(),LoginSignup.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.show();
                        }
                        else{

                            try {
                                JSONObject jObject = new JSONObject(response);

                                nickname.setText((String) jObject.get("nickName"));
                                firstname.setText((String) jObject.get("firstName"));
                                lastname.setText((String) jObject.get("lastName"));
                                gender.setText((String) jObject.get("gender"));
                                email.setText((String) jObject.get("email"));
                                password.setText((String) jObject.get("password"));

                                //Log.i("test", (String) jObject.get("nickName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("userid",userId);

                return params;
            }
        };

        MySingleton.getmInstance(Profile.this).addToRequestqueue(stringRequest);


        //Log.i("test",nickname.getText().toString());


    }

    public void delete_user(){

        String serverUrlDeleteUser=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/delete_user";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrlDeleteUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i("test",response);
                        if(response.equals("1")){
                            Toast.makeText(getApplicationContext(),"User Successfully deleted",Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(Profile.this,MainScreen.class);
                            in.putExtra("user_id",user_id);
                            startActivity(in);
                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Error deleting user",Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this,"Error..",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("user_id",user_id);
                return params;
            }
        };

        MySingleton.getmInstance(Profile.this).addToRequestqueue(stringRequest);

    }
}
