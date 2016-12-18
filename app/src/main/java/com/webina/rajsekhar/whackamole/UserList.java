package com.webina.rajsekhar.whackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class UserList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String serverUrl;



    ArrayList<String> nickNameList=new ArrayList<String>();
    ArrayList<String> firstNameList=new ArrayList<String>();
    ArrayList<String> user_ids=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView=(RecyclerView)findViewById(R.id.userListRV);


        serverUrl=getResources().getString(R.string.server_ip)+"android_buddies/index.php/welcome/get_userList";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jArray;


                        try {
                            jArray  = new JSONArray(response);

                            int count=0;
                            String fdNickname,fdname,uid;

                            while (count<jArray.length()){
                                JSONObject JO=jArray.getJSONObject(count);



                                fdNickname=JO.getString("nickName");
                                fdname=JO.getString("firstName");
                                uid=JO.getString("id");

                                //Log.i("test",fdNickname.toString());
                                //Log.i("test",fdname.toString());

                                nickNameList.add(fdNickname);
                                firstNameList.add(fdname);
                                user_ids.add(uid);

                                count++;
                            }

                            String[] nickNameArray=nickNameList.toArray(new String[nickNameList.size()]);
                            String[] firstNameArray=firstNameList.toArray(new String[firstNameList.size()]);
                            String[] userIdArray=user_ids.toArray(new String[user_ids.size()]);

                            adapter=new RecyclerAdapterUserList(nickNameArray,firstNameArray,userIdArray,getApplicationContext());

                            layoutManager=new LinearLayoutManager(UserList.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserList.this,"Error..",Toast.LENGTH_SHORT).show();
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

        MySingleton.getmInstance(UserList.this).addToRequestqueue(stringRequest);




    }
}
