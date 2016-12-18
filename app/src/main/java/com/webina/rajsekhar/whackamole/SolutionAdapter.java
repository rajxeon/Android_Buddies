package com.webina.rajsekhar.whackamole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RajSekhar on 12/11/2016.
 */
public class SolutionAdapter extends ArrayAdapter {

    List list=new ArrayList();

    public SolutionAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(SolutionsParser object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
        list.remove(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        SolutionHolder solutionHolder;

        if(row==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            solutionHolder=new SolutionHolder();
            solutionHolder.tx_name= (TextView) row.findViewById(R.id.solution_user_card);
            solutionHolder.tx_title= (TextView) row.findViewById(R.id.solution_title_card);
            solutionHolder.tx_body= (TextView) row.findViewById(R.id.solution_body_card);
            row.setTag(solutionHolder);
        }
        else{
            solutionHolder= (SolutionHolder) row.getTag();
        }
        SolutionsParser solutionsParser=(SolutionsParser)this.getItem(position);
        solutionHolder.tx_name.setText(solutionsParser.getName());
        solutionHolder.tx_title.setText(solutionsParser.getTitle());
        solutionHolder.tx_body.setText(solutionsParser.getBody());
        return row;
    }

    static class SolutionHolder{
        TextView tx_name,tx_title,tx_body;
    }
}
