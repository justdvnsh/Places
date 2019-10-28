package com.example.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class placesAdapter extends RecyclerView.Adapter<placesAdapter.placesViewHolder> {

    private ArrayList<HashMap<String, String>> places;

    final private ListItemOnClickListener mOnClickListener;

    public void setPlaces(ArrayList<HashMap<String, String>> userPlaces) {
        places = userPlaces;
        notifyDataSetChanged();
    }

    public interface ListItemOnClickListener {
        void onClick(HashMap<String, String> place);
    }

    public placesAdapter(ListItemOnClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public placesAdapter.placesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.places_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        placesViewHolder viewHolder = new placesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull placesAdapter.placesViewHolder holder, int position) {
        
        holder.bind(position);
        
    }

    @Override
    public int getItemCount() {
        if ( null == places ) return 0;
        return places.size();
    }

    public class placesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mPlacesTextView;

        public placesViewHolder(View view) {
            super(view);

            mPlacesTextView = (TextView) view.findViewById(R.id.place);
            mPlacesTextView.setOnClickListener(this);
        }

        public void bind(int position) {

            HashMap<String, String> place = places.get(position);
            mPlacesTextView.setText("Hello");

        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            HashMap<String, String> place = places.get(clickedPosition);
            mOnClickListener.onClick(place);

        }
    }
}
