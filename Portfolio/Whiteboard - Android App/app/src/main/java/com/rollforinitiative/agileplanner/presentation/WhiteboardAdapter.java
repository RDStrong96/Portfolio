package com.rollforinitiative.agileplanner.presentation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Whiteboard;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WhiteboardAdapter extends RecyclerView.Adapter<WhiteboardAdapter.WhiteboardViewHolder> {
    private List<Whiteboard> whiteboards;
    private ItemClickListener listener;

    // Provide a reference to the views for each data item
    public static class WhiteboardViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView WhiteboardTitle;
        public TextView WhiteboardSubtitle;

        public WhiteboardViewHolder(View v) {
            super(v);
            view = v;
            WhiteboardTitle = (TextView) v.findViewById(R.id.whiteboardTitle);
        }

    }

    // Constructor for list adapter initializes the list
    public WhiteboardAdapter(ArrayList<Whiteboard> whiteboards, ItemClickListener listener) {
        this.whiteboards = whiteboards;
        this.listener = listener;
    }

    // Create new list items (invoked by the layout manager)
    @Override
    public WhiteboardAdapter.WhiteboardViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new list item view from the XML file
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whiteboard_list_item, parent, false);


        WhiteboardAdapter.WhiteboardViewHolder holder = new WhiteboardAdapter.WhiteboardViewHolder(listItem);
        return holder;
    }

    // Set the contents of a list item when it is created
    @Override
    public void onBindViewHolder(final WhiteboardAdapter.WhiteboardViewHolder holder, int position) {
        // - holder gives the view holder for the list item
        // - position gives the index of the list
        holder.WhiteboardTitle.setText(whiteboards.get(position).getName());

        //create an onClickListener for each item in the list
        //selected Whiteboard is Whiteboards.get(holder.getAdapterPosition())
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked whiteboard at position " + holder.getAdapterPosition());
                Log.d(TAG, Integer.toString(whiteboards.get(holder.getAdapterPosition()).getWhiteboardID()));

                //tell the activity which whiteboard was clicked
                listener.onClick(whiteboards.get(holder.getAdapterPosition()));
            }
        });
    }

    // Return the number of items in the list
    @Override
    public int getItemCount() {
        //the Whiteboards list will initially be null, so trying to access the size of it will result
        //in a runtime error
        if (whiteboards == null) {
            return 0;
        }
        else {
            return whiteboards.size();
        }
    }

    //public method called by mainactivity when the database of tables is updated,
    // it takes the new list of Whiteboards and notifies itself that the data has changed so that
    // the list in the recyclerview can be updated
    public void setWhiteboards(List<Whiteboard> whiteboards) {
        this.whiteboards = whiteboards;
        notifyDataSetChanged();
    }
}
