package com.example.ContentProviders;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.DAO.MercedesDB;

public class ProductContentProvider extends ContentProvider {

    //region Initiation
    public static final String AUTHORITY = "com.example.ContentProviders.ProductContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/products");
    private static final int SUGGESTIONS_PRODUCT = 1;
    private static final int SEARCH_PRODUCT = 2;
    private static final int GET_PRODUCT = 3;
    MercedesDB myDB = null;
    private UriMatcher uriMatcher = builtUriMatcher();
    //endregion

    //region Override Methods
    @Override
    public boolean onCreate() {
        myDB = new MercedesDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        switch (uriMatcher.match(uri)) {
            case SUGGESTIONS_PRODUCT:
                c = myDB.getProductDataForSearching(selectionArgs);
                break;
            case SEARCH_PRODUCT:
                c = myDB.getProductDataForSearching(selectionArgs);
                break;
            case GET_PRODUCT:
                String id = uri.getLastPathSegment();
                c = myDB.getProductDetailDataForSearching(id);
        }

        return c;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    //endregion

    public UriMatcher builtUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGESTIONS_PRODUCT);

        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/countries" of searchable.xml
        uriMatcher.addURI(AUTHORITY, "products", SEARCH_PRODUCT);

        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Country details for CountryActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CountryDB.java
        uriMatcher.addURI(AUTHORITY, "product/#", GET_PRODUCT);

        return uriMatcher;
    }
}