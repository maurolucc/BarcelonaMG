package com.example.mauro.barcelonamg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauro.barcelonamg.R;
import com.example.mauro.barcelonamg.model.Dades;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter {

    private List<Dades> mDades;
    private Context mContext;
    private int mResource;

    public MyCustomAdapter(Context context, List<Dades> data){
        super(context, R.layout.custom_item, data);
        this.mContext = context;
        this.mDades = data;
        this.mResource = R.layout.custom_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //demanes al context, a la pantalla, poder omplenar-lo

        View rowView = layoutInflater.inflate(mResource,parent, false);
        //vull modificar(inflate) AQUESTA vista(mResource)

        TextView nom = (TextView) rowView.findViewById(R.id.nom);
        TextView text = (TextView) rowView.findViewById(R.id.text);
        ImageView icono = (ImageView) rowView.findViewById(R.id.icono);
        ImageView imatge = (ImageView) rowView.findViewById(R.id.imatge);
        //separem cada dada per poder-les modificar

        nom.setText(mDades.get(position).nom);
        text.setText(mDades.get(position).text);
        icono.setImageBitmap(mDades.get(position).icono);
        imatge.setImageBitmap(mDades.get(position).imatge);
        //especifiquem quan val cada nova dada

        return rowView;
    }
}