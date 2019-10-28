package com.example.places;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class placesAdapter extends RecyclerView.Adapter<placesAdapter.placesViewHolder> {

    private List<Address> places;

    final private ListItemOnClickListener mOnClickListener;

    public void setPlaces(List<Address> userPlaces) {
        places = userPlaces;
        notifyDataSetChanged();
    }

    public interface ListItemOnClickListener {
        void onClick(Address place);
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

            Address place = places.get(position);
            String feature;
            String thoroughFare;
            String locality;
            String subAdmin;
            String admin;
            String postalCode;
            String country;

            if (place.getThoroughfare() != null && place.getLocality() != null) {

                feature = place.getFeatureName();
                thoroughFare = place.getThoroughfare();
                locality = place.getLocality();
                subAdmin = place.getSubAdminArea();
                admin = place.getAdminArea();
                postalCode = place.getPostalCode();
                country = place.getCountryName();
                mPlacesTextView.setText(feature + " - " + thoroughFare + " - " + locality + " - " + subAdmin + " - " + admin + " - " + country);
            } else {

                feature = place.getFeatureName();
                subAdmin = place.getSubAdminArea();
                admin = place.getAdminArea();
                postalCode = place.getPostalCode();
                country = place.getCountryName();
                mPlacesTextView.setText(feature + " - " + subAdmin + " - " + admin + " - " + country);

            }

        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            Address place = places.get(clickedPosition);
            mOnClickListener.onClick(place);

        }
    }
}
