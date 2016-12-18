package com.webina.rajsekhar.whackamole;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by RajSekhar on 12/16/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    String[] country_names,country_capitals;

    public RecyclerAdapter(String[] country_names,String[] country_capitals){
        this.country_capitals=country_capitals;
        this.country_names=country_names;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tx_Country.setText(country_names[position]);
        holder.tx_Capital.setText(country_capitals[position]);
    }

    @Override
    public int getItemCount() {
        return country_names.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tx_Country,tx_Capital;
        public RecyclerViewHolder(View view        ) {
            super(view);
            tx_Capital=(TextView)view.findViewById(R.id.feedbackText);
            tx_Country=(TextView)view.findViewById(R.id.user_name);

        }
    }
}








