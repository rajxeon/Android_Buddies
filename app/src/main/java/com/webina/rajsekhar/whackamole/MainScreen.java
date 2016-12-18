package com.webina.rajsekhar.whackamole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {
    String user_id;
    Button guide_dictionary,upload_guildeLine,viewUserList,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_id = getIntent().getStringExtra("user_id");


        setContentView(R.layout.activity_main_screen);

        Button profile=(Button)findViewById(R.id.profile);
        Button viewUserList=(Button)findViewById(R.id.viewUserList);
        Button upload_guildeLine=(Button)findViewById(R.id.guide_dictionary_upload);
        logout=(Button)findViewById(R.id.logout);

        if(!user_id.equals("1")){
            upload_guildeLine.setVisibility(View.INVISIBLE);
            viewUserList.setVisibility(View.INVISIBLE);
        }

        guide_dictionary=(Button)findViewById(R.id.guide_dictionary);
        Button solutions=(Button)findViewById(R.id.solutions);

        viewUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UserList.class);
                startActivity(i);
            }
        });


        upload_guildeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),PostGuideLine.class);
                startActivity(i);
            }
        });

        solutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Solutions.class);
                i.putExtra("user_id",user_id);
                i.putExtra("keyword","");
                startActivity(i);
            }
        });



        guide_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Guide.class);
                startActivity(i);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Profile.class);
                i.putExtra("user_id",user_id);
                i.putExtra("from_admin","0");
                startActivity(i);
            }
        });


        Button game=(Button)findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),WhackAMoleActivity.class);
                startActivity(i);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }


}
