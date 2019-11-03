package com.example.places;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.os.Bundle;

import com.example.places.data.placesContract;
import com.example.places.data.placesDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements placesAdapter.ListItemOnClickListener {

    /* initializaiton of the variables */
    private RecyclerView mRecyclerView;
    protected static placesAdapter mAdapter;
    TextView mInfoTextView;
    ProgressBar mProgressBar;
    protected static SQLiteDatabase mDb;

    protected static Cursor getAllPlaces() {

        return mDb.query(
                placesContract.placesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                placesContract.placesEntry.COLUMN_DATE
        );

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // the floating action button to open Maps intent
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Maps Activity
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        mInfoTextView = (TextView) findViewById(R.id.info);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        // setting the layout and adapter for the recycler view.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        placesDbHelper dbHelper = new placesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllPlaces();

        mAdapter = new placesAdapter(this, cursor);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                removePlace(id);
                mAdapter.swapCursor(getAllPlaces());

            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean removePlace(long id) {
        return mDb.delete(placesContract.placesEntry.TABLE_NAME,
                placesContract.placesEntry._ID + "=" + id, null) > 0;
    }

    // onClick method for each item in the recycker view ( mostly places )
    @Override
    public void onClick(Address place) {
//        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        double latitude = place.getLatitude();
//        double longitude = place.getLongitude();
//        intent.putExtra("lat", latitude);
//        intent.putExtra("long", longitude);
//        startActivity(intent);
    }
}
