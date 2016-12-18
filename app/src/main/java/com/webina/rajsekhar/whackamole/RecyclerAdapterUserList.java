package com.webina.rajsekhar.whackamole;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by RajSekhar on 12/16/2016.
 */
public class RecyclerAdapterUserList extends RecyclerView.Adapter<RecyclerAdapterUserList.RecyclerViewHolder> {
    String[] country_names,country_capitals,user_ids;
    Context ctx;

    public RecyclerAdapterUserList(String[] country_names, String[] country_capitals,String[] user_ids,Context ctx){
        this.country_capitals=country_capitals;
        this.country_names=country_names;
        this.user_ids=user_ids;
        this.ctx=ctx;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view,ctx);
        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tx_Country.setText(country_names[position]);
        holder.tx_Capital.setText(country_capitals[position]);
        holder.user_id=user_ids[position];
    }

    @Override
    public int getItemCount() {
        return country_names.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tx_Country,tx_Capital;
        String user_id;
        Context context;

        public RecyclerViewHolder(View view,Context context) {
            super(view);
            this.context=context;
            view.setOnClickListener(this);
            tx_Capital=(TextView)view.findViewById(R.id.feedbackText);
            tx_Country=(TextView)view.findViewById(R.id.user_name);

        }

        @Override
        public void onClick(View view) {

            Log.i("test", String.valueOf(this.user_id));
            Intent i=new Intent(context, Profile.class);
            i.putExtra("user_id",user_id);
            i.putExtra("from_admin","1");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}








