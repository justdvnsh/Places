package com.example.places;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.places.data.placesContract;

import java.util.List;

public class placesAdapter extends RecyclerView.Adapter<placesAdapter.placesViewHolder> {

    private Cursor mCursor;

    final private ListItemOnClickListener mOnClickListener;

    public interface ListItemOnClickListener {
        void onClick(Address place);
    }

    public placesAdapter(ListItemOnClickListener listener, Cursor cursor) {
        mOnClickListener = listener;
        mCursor = cursor;
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
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }


    public class placesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mPlacesTextView;

        public placesViewHolder(View view) {
            super(view);

            mPlacesTextView = (TextView) view.findViewById(R.id.place);
            mPlacesTextView.setOnClickListener(this);
        }

        public void bind(int position) {

            if (!mCursor.moveToPosition(position)) {
                return ;
            }

            String feature = mCursor.getString(mCursor.getColumnIndex(placesContract.placesEntry.COLUMN_FEATURE));
            String admin = mCursor.getString(mCursor.getColumnIndex(placesContract.placesEntry.COLUMN_ADMIN));
            String country = mCursor.getString(mCursor.getColumnIndex(placesContract.placesEntry.COLUMN_COUNTRY_NAME));
            long id = mCursor.getLong(mCursor.getColumnIndex(placesContract.placesEntry._ID));

            itemView.setTag(id);

            mPlacesTextView.setText(feature + admin + country);

        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
//            Address place = places.get(clickedPosition);
//            mOnClickListener.onClick(place);

        }
    }
}
