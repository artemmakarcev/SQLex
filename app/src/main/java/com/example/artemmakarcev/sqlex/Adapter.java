package com.example.artemmakarcev.sqlex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artemmakarcev.sqlex.POJO.GetAllEx;

import java.util.List;

public class Adapter extends ArrayAdapter<String> {

    public List<GetAllEx> data;
    private Context context;

    Adapter(Context context, List<GetAllEx> data) {
        super(context, R.layout.listview);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getId();
    }

//    String getUrlImage(int position) {
//        return data.get(position).getImage();
//    }

//    String getNameTitle(int position) {
//        return data.get(position).getTitle();
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.listview, parent, false);

//        TextView title = (TextView) view.findViewById(R.id.title);
        TextView type = (TextView) view.findViewById(R.id.nameEx);
        TextView description = (TextView) view.findViewById(R.id.descriptionEx);

        GetAllEx objectItem = data.get(position);

//        title.setText(objectItem.getName());
        type.setText(objectItem.getDescription());
        description.setText(objectItem.getDescription());


        return view;
    }

}