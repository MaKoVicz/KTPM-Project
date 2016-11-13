package com.example.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.DTO.Product;
import com.example.DTO.ProductCategory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MercedesDB extends SQLiteOpenHelper {

    //region Initiation
    private static final String DB_PATH = "/data/data/com.example.mercedesapp/databases/";
    private static final String DB_NAME = "MercedesDB.db";
    private final Context context;
    private SQLiteDatabase myDB;
    //endregion

    //region Create Database
    public MercedesDB(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (myDB != null)
            myDB.close();

        super.close();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
    //endregion

    //region Queries
    public ArrayList<ProductCategory> getProductCategoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM ProductCategory";
        Cursor cursor = db.rawQuery(SqlCmd, null);
        ArrayList<ProductCategory> categoies = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                ProductCategory category = new ProductCategory();
                category.setName(cursor.getString(0));
                category.setImageURL(cursor.getString(1));
                categoies.add(category);
            } while (cursor.moveToNext());
        }

        return categoies;
    }

    public ArrayList<Product> getProductData(String category) {
        category = "'" + category + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM Product WHERE Category = " + category;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        ArrayList<Product> products = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setName(cursor.getString(0));
                product.setColor(cursor.getString(1));
                product.setPrice(cursor.getDouble(2));
                product.setCategory(cursor.getString(3));
                product.setDescription(cursor.getString(4));
                product.setPic1(cursor.getString(5));
                product.setPic2(cursor.getString(6));
                product.setPic3(cursor.getString(7));
                product.setPic4(cursor.getString(8));
                product.setPic5(cursor.getString(9));

                products.add(product);
            } while (cursor.moveToNext());
        }

        return products;
    }

    public Product getProductDetailInformation(String name) {
        name = "'" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM Product WHERE Name = " + name;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        Product product = new Product();

        if (cursor.moveToFirst()) {
            do {
                product.setName(cursor.getString(0));
                product.setColor(cursor.getString(1));
                product.setPrice(cursor.getDouble(2));
                product.setCategory(cursor.getString(3));
                product.setDescription(cursor.getString(4));
                product.setPic1(cursor.getString(5));
                product.setPic2(cursor.getString(6));
                product.setPic3(cursor.getString(7));
                product.setPic4(cursor.getString(8));
                product.setPic5(cursor.getString(9));
            } while (cursor.moveToNext());
        }

        return product;
    }
    //endregion
}
