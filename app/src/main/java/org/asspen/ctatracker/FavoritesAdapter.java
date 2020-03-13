package org.asspen.ctatracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavViewHolder> {
    private ArrayList<String> mDatasetId;
    private ArrayList<String> mDatasetLabel;
    Context con;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoritesAdapter(Context c, ArrayList<String> aId, ArrayList<String> aLabel) {
        con = c;
        mDatasetId = aId;
        mDatasetLabel = aLabel;
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {

        TextView txtId;
        TextView txtLabel;
        FavViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtName);
            txtLabel = itemView.findViewById(R.id.txtAddress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), ResultActivity.class);
                    i.putExtra("stopcode", Integer.parseInt(txtId.getText().toString()));
                    Log.d("value? hello?", txtId.getText().toString());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesAdapter.FavViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);

        FavViewHolder vh = new FavViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtId.setText(mDatasetId.get(position));
        holder.txtLabel.setText(mDatasetLabel.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetLabel.size();
    }
}
