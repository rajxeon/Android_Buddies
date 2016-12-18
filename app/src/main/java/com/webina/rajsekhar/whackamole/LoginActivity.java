package com.webina.rajsekhar.whackamole;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

public class LoginActivity extends AppCompatActivity {

    EditText Email,Password;
    String serverUrl;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_button=(Button)findViewById(R.id.email_sign_in_button);

        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/login";
        Email    =(EditText)findViewById(R.id.email);
        Password =(EditText)findViewById(R.id.password);
        builder=new AlertDialog.Builder(LoginActivity.this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email,password;
                email   =Email.getText().toString();
                password=Password.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if(Integer.parseInt(response)>0){
                                    //Log.i("test",response);
                                    Intent i =new Intent(getApplicationContext(),MainScreen.class);
                                    i.putExtra("user_id",response);
                                    startActivity(i);

                                }
                                else{
                                    builder.setTitle("Login error");
                                    builder.setMessage("Please provide a valid username and password");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Email.setText("");
                                            Password.setText("");
                                        }
                                    });
                                    AlertDialog alertDialog=builder.create();
                                    alertDialog.show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this,"Error..",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("email",email);
                        params.put("password",password);
                        return params;
                    }
                };

                MySingleton.getmInstance(LoginActivity.this).addToRequestqueue(stringRequest);


            }
        });

    }
}
