package com.example.mauro.barcelonamg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauro.barcelonamg.R;
import com.example.mauro.barcelonamg.interfices.NotifyFetch;
import com.example.mauro.barcelonamg.model.Discoteca;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Guifré on 12/02/2015.
 */
public class DiscotecaListAdapter extends ParseQueryAdapter<Discoteca> {
    private Context mContext;
    private int mResource;


    public DiscotecaListAdapter(Context context, ParseQueryAdapter.QueryFactory<Discoteca> queryFactory) {
        super(context, queryFactory);
        this.mContext = context;
        this.mResource = R.layout.custom_item;
    }

    @Override
    public View getItemView(Discoteca object, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //demanes al context, a la pantalla, poder omplenar-lo

        View rowView = layoutInflater.inflate(mResource,parent, false);
        //vull modificar(inflate) AQUESTA vista(mResource)

        TextView nom = (TextView) rowView.findViewById(R.id.nom);
        TextView text = (TextView) rowView.findViewById(R.id.text);
        ImageView icono = (ImageView) rowView.findViewById(R.id.icono);
        ImageView imatge = (ImageView) rowView.findViewById(R.id.imatge);
        //separem cada dada per poder-les modificar

        nom.setText(object.getName());
        text.setText(object.getDescripcio());

        try {
            byte[] data = object.getIcon().getData();
            Bitmap icn = BitmapFactory.decodeByteArray(data, 0, data.length);
            icono.setImageBitmap(icn);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            byte[] data = object.getImage().getData();
            Bitmap icn = BitmapFactory.decodeByteArray(data, 0, data.length);
            imatge.setImageBitmap(icn);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}