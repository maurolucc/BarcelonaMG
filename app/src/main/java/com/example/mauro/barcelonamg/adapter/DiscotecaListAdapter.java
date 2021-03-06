package com.example.mauro.barcelonamg.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauro.barcelonamg.Comments;
import com.example.mauro.barcelonamg.ItemClickat;
import com.example.mauro.barcelonamg.R;
import com.example.mauro.barcelonamg.model.Discoteca;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

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
        final Button like = (Button) rowView.findViewById(R.id.likeButton);
        final Button comm = (Button) rowView.findViewById(R.id.commentButton);
        //separem cada dada per poder-les modificar

        nom.setText(object.getName());
        text.setText(object.getDescripcio());

        new DownloadImageTask(object,icono).execute("");
        new DownloadImageTaskk(object,imatge).execute("");

        final Discoteca utilitzar = object;
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Leisure");
                query.getInBackground(utilitzar.getObjectId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.increment("numLikes");
                            object.saveInBackground();
                            Number numlikes = object.getNumber("numLikes");
                            like.setText("Likes "+numlikes);
                        } else {
                            Log.e("error like", "no sha pogut posar el me gusta");
                        }
                    }
                });
            }
        });

        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llencarItemClick = new Intent(mContext, Comments.class);
                llencarItemClick.putExtra("KEY", utilitzar.getObjectId());
                mContext.startActivity(llencarItemClick);
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llencarItemClick = new Intent(mContext, ItemClickat.class);
                llencarItemClick.putExtra("KEY", utilitzar.getObjectId());
                mContext.startActivity(llencarItemClick);
            }
        });


        return rowView;
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Discoteca obj;
    private ImageView img;

    public DownloadImageTask(Discoteca disco, ImageView img){
        obj = disco;
        this.img = img;
    }

    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    protected Bitmap doInBackground(String... params) {
        byte[] data = new byte[0];
        try {
            data = obj.getIcon().getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(Bitmap result) {
        img.setImageBitmap(result);
    }
}

class DownloadImageTaskk extends AsyncTask<String, Void, Bitmap> {
    private Discoteca obj;
    private ImageView img;

    public DownloadImageTaskk(Discoteca disco, ImageView img){
        obj = disco;
        this.img = img;
    }

    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    protected Bitmap doInBackground(String... params) {
        byte[] data = new byte[0];
        try {
            data = obj.getImage().getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(Bitmap result) {
        img.setImageBitmap(result);
    }
}