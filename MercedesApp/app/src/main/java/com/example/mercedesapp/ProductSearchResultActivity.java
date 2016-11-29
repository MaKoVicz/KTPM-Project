package com.example.mercedesapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductSearchResultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //region Initiation
    private Uri mUri;
    private ImageView headerImg;
    private TextView title, price, detail;
    private Button btnTestDriveRegister;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        mUri = intent.getData();

        headerImg = (ImageView) findViewById(R.id.product_header_img);
        title = (TextView) findViewById(R.id.product_detail_name_txtv);
        price = (TextView) findViewById(R.id.product_detail_price_txtv);
        detail = (TextView) findViewById(R.id.product_detail_description_txtv);
        btnTestDriveRegister = (Button) findViewById(R.id.test_drive_register_button);

        //Invokes the method onCreateloader() in non-ui thread
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getBaseContext(), mUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            title.setText(cursor.getString(0));
            price.setText(cursor.getString(2));
            detail.setText(cursor.getString(4));
            Picasso.with(this)
                    .load(cursor.getString(5))
                    .placeholder(R.drawable.ic_vector_image_loading)
                    .into(headerImg);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion
}
