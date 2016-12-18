package com.webina.rajsekhar.whackamole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LoginSignup extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_signup);




    }

    public void signUpListener(View v){
        Intent i=new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(i);

    }

    public void loginListener(View v){
        Intent i =new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);


    }


}
