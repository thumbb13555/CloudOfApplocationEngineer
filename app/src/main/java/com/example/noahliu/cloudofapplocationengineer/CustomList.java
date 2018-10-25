package com.example.noahliu.cloudofapplocationengineer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter {
    private Activity context;
    private String[] id;
    private String[] worktype;
    private String[] datatime;
    //private String[] age;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);

        TextView tvID = (TextView) listViewItem.findViewById(R.id.tvID);
        TextView tvFN = (TextView) listViewItem.findViewById(R.id.tvFN);
        TextView tvLN = (TextView) listViewItem.findViewById(R.id.tvLN);
        //TextView tvage = (TextView) listViewItem.findViewById(R.id.tvage);

        tvID.setText(id[position]);
        tvFN.setText(worktype[position]);
        tvLN.setText(datatime[position]);
        //tvage.setText(age[position]);

        return listViewItem;
    }

    public CustomList(Activity context, String[] id, String[] worktype, String[] datatime/*, String[] age*/) {
        super(context, R.layout.list_view_layout, id);
        this.context = context;
        this.id = id;
        this.worktype = worktype;
        this.datatime = datatime;
        //this.age = age;
    }
}
