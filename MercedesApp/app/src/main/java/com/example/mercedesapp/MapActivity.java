package com.example.mercedesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.DTO.Coordinate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myGoogleMap;

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_layout);
        mapFragment.getMapAsync(this);

        new JSONAsyncTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        setTitle("Mercedes");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMain();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToMain();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
    }
    //endregion

    public void returnToMain() {
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class JSONAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "HttpHandler";
        ArrayList<Coordinate> coordinateArrayList;
        //region Initiation
        private Context context;
        private ProgressDialog progressDialog;
        //endregion

        //region Personal Methods
        public JSONAsyncTask(Context context) {
            this.context = context;
        }

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        private String trimRedundance(String stringToTrim) {
            int trimPosition = stringToTrim.lastIndexOf("}");
            return stringToTrim.substring(trimPosition + 1);
        }
        //endregion

        //region Override Methods
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String jsonStr = makeServiceCall("http://mapcoordinateservice.somee.com/Location/ReturnCoordinate");
            jsonStr = jsonStr.replace(trimRedundance(jsonStr), "");
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    coordinateArrayList = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray coordinates = jsonObj.getJSONArray("Coordinates");

                    for (int i = 0; i < coordinates.length(); i++) {
                        Coordinate coordinate = new Coordinate();
                        coordinate.setLatitude(coordinates.getJSONObject(i).getDouble("Latitude"));
                        coordinate.setLongitude(coordinates.getJSONObject(i).getDouble("Longitude"));
                        coordinate.setLocation(coordinates.getJSONObject(i).getString("Location"));
                        coordinateArrayList.add(coordinate);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void mVoid) {
            super.onPostExecute(mVoid);
            progressDialog.dismiss();

            LatLng mark = new LatLng(coordinateArrayList.get(1).getLongitude(), coordinateArrayList.get(1).getLatitude());
            myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, 16));
            myGoogleMap.addMarker(new MarkerOptions().title("Mercedes Vietnam").position(mark));
        }
        //endregion
    }
}