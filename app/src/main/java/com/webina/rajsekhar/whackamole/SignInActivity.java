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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    EditText nickname,firstname,lastname,gender,email,password;
    Button submit;
    String serverUrl;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        nickname    =(EditText)findViewById(R.id.nickname_input);
        firstname   =(EditText)findViewById(R.id.firstname_input);
        lastname    =(EditText)findViewById(R.id.lastname_input);
        gender      =(EditText)findViewById(R.id.gender_input);
        email       =(EditText)findViewById(R.id.email_input);
        password    =(EditText)findViewById(R.id.password_input);
        submit      =(Button) findViewById(R.id.submit);

        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/signup";
        builder=new AlertDialog.Builder(SignInActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String _nickname,_firstname,_lastname,_gender,_email,_password;
                _nickname   =nickname.getText().toString();
                _firstname  =firstname.getText().toString();
                _lastname   =lastname.getText().toString();
                _gender     =gender.getText().toString();
                _email      =email.getText().toString();
                _password   =password.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if(response.equals("0")){
                                    builder.setTitle("Sign up error");
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

                                else if(response.equals("-1")){
                                    //Duplicate email address
                                    builder.setTitle("Duplicate data error");
                                    builder.setMessage("Email already exists in the database");
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

                                    Toast.makeText(SignInActivity.this,"Successfully signed up",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),MainScreen.class);
                                    i.putExtra("user_id",response);
                                    startActivity(i);
                                   //the response contains the insert id

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignInActivity.this,"Error..",Toast.LENGTH_SHORT).show();
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

                MySingleton.getmInstance(SignInActivity.this).addToRequestqueue(stringRequest);


                //Log.i("test",nickname.getText().toString());
            }
        });
    }
}
