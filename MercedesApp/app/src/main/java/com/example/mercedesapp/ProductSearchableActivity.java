package com.example.mercedesapp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ContentProviders.ProductContentProvider;

public class ProductSearchableActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView productListView;
    private SimpleCursorAdapter mCursorAdapter;

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Getting reference to Country List
        productListView = (ListView) findViewById(R.id.productListView);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);

                // Creating a uri to fetch country details corresponding to selected listview item
                Uri data = Uri.withAppendedPath(ProductContentProvider.CONTENT_URI, String.valueOf(id));

                // Setting uri to the data on the intent
                intent.setData(data);

                // Open the activity
                startActivity(intent);
            }
        });

        // Defining CursorAdapter for the ListView
        mCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1}, 0);

        // Setting the cursor adapter for the country listview
        productListView.setAdapter(mCursorAdapter);

        // Getting the intent that invoked this activity
        Intent intent = getIntent();

        // If this activity is invoked by selecting an item from Suggestion of Search dialog or
        // from listview of SearchActivity
        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
            Intent productIntent = new Intent(this, ProductDetailActivity.class);
            productIntent.setData(intent.getData());
            startActivity(productIntent);
            finish();
        } else if (intent.getAction().equals(Intent.ACTION_SEARCH)) { // If this activity is invoked, when user presses "Go" in the Keyboard of Search Dialog
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle data) {
        Uri uri = ProductContentProvider.CONTENT_URI;
        return new CursorLoader(getBaseContext(), uri, null, null, new String[]{data.getString("query")}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion

    private void doSearch(String query) {
        Bundle data = new Bundle();
        data.putString("query", query);

        // Invoking onCreateLoader() in non-ui thread
        getSupportLoaderManager().initLoader(1, data, this);
    }
}
