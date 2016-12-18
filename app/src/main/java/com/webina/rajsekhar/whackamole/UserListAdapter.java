package com.webina.rajsekhar.whackamole;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by RajSekhar on 12/17/2016.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.RecyclerViewHolder> {

    String[] userList={"User 1","User 2"};

    public UserListAdapter(String[] userList){
        this.userList=userList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row,parent,false);

        RecyclerAdapter.RecyclerViewHolder recyclerViewHolder= new RecyclerAdapter.RecyclerViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txUser.setText(userList[position]);
    }



    @Override
    public int getItemCount() {
        return userList.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txUser;
        public RecyclerViewHolder(View view) {
            super(view);

            txUser=(TextView) view.findViewById(R.id.userListRow);
        }
    }
}
