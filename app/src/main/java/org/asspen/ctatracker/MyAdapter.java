package org.asspen.ctatracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FavViewHolder> {
    private ArrayList<String> mDataset0;
    private ArrayList<String> mDataset1;

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
    public MyAdapter(Context c, ArrayList<String> al, ArrayList<String> al1) {
        mDataset = myDataset;
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {

        private TextView txtId;
        private TextView txtDesc;
        FavViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtAddress);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.FavViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        FavViewHolder vh = new FavViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtDesc.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
