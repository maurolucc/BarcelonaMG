package com.example.mauro.barcelonamg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mauro.barcelonamg.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class CustomCommentAdapter extends ParseQueryAdapter<ParseObject> {

    public CustomCommentAdapter(Context context, final String a) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Comments");
                query.whereContains("referenceObjectId", a);
                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.custom_item_comment, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.qui);
        titleTextView.setText(object.getString("user"));

        // Add the comment view
        TextView timestampView = (TextView) v.findViewById(R.id.que);
        timestampView.setText(object.getString("comment"));
        return v;
    }
}
