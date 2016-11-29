package com.example.DAO;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.DTO.Product;
import com.example.DTO.ProductCategory;
import com.example.DTO.TestDrive;
import com.example.DTO.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
    public HashMap<String, String> mapping() {
        // This HashMap is used to map table fields to Custom Suggestion fields
        HashMap<String, String> mAliasMap = new HashMap<String, String>();

        // Unique id for the each Suggestions ( Mandatory )
        mAliasMap.put("_ID", "Name" + " as " + "_id");

        // Text for Suggestions ( Mandatory )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, "Name" + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);

        // This value will be appended to the Intent data on selecting an item from Search result or Suggestions ( Optional )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "Name" + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);

        return mAliasMap;
    }

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
        cursor.close();

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
                product.setPrice(cursor.getString(2));
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
        cursor.close();

        return products;
    }

    public ArrayList<TestDrive> getAllTestDriveData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM TestDrive";
        Cursor cursor = db.rawQuery(SqlCmd, null);
        ArrayList<TestDrive> testDriveArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                TestDrive testDrive = new TestDrive();
                testDrive.setName(cursor.getString(0));
                testDrive.setAddress(cursor.getString(1));
                testDrive.setPhone(cursor.getString(2));
                testDrive.setEmail(cursor.getString(3));
                testDrive.setRegisterDate(cursor.getString(4));
                testDrive.setTestProduct(cursor.getString(5));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return testDriveArrayList;
    }

    public TestDrive getTestDriveDetailData(String registerDate) {
        registerDate = "'" + registerDate + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM TestDrive WHERE RegisterDate = " + registerDate;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        TestDrive testDrive = new TestDrive();

        if (cursor.moveToFirst()) {
            testDrive.setName(cursor.getString(0));
            testDrive.setAddress(cursor.getString(1));
            testDrive.setPhone(cursor.getString(2));
            testDrive.setEmail(cursor.getString(3));
            testDrive.setRegisterDate(cursor.getString(4));
            testDrive.setTestProduct(cursor.getString(5));
        }

        cursor.close();
        return testDrive;
    }

    public Cursor getProductDetailDataForSearching(String id) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables("Product");
        Cursor c = queryBuilder.query(this.getReadableDatabase(),
                new String[]{"Name", "Color", "Price", "Category", "Description", "Pic1"},
                "Name = ?", new String[]{id}, null, null, null, "1"
        );

        return c;
    }

    public Cursor getProductDataForSearching(String[] selectionArgs) {
        String selection = "Name" + " like ? ";

        if (selectionArgs != null) {
            selectionArgs[0] = "%" + selectionArgs[0] + "%";
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setProjectionMap(mapping());
        queryBuilder.setTables("Product");

        Cursor c = queryBuilder.query(this.getReadableDatabase(),
                new String[]{"_ID",
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID},
                selection,
                selectionArgs,
                null,
                null,
                "Name" + " asc ", "10"
        );
        return c;
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
                product.setPrice(cursor.getString(2));
                product.setCategory(cursor.getString(3));
                product.setDescription(cursor.getString(4));
                product.setPic1(cursor.getString(5));
                product.setPic2(cursor.getString(6));
                product.setPic3(cursor.getString(7));
                product.setPic4(cursor.getString(8));
                product.setPic5(cursor.getString(9));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return product;
    }

    public int getUserEmailForSignUpCheck(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        email = "'" + email + "'";

        String SqlCmd = "SELECT * FROM User WHERE Email = " + email;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount; //Number of rows in the cursor
    }

    public int getUserDataForLoginCheck(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        username = "'" + username + "'";
        password = "'" + password + "'";

        String SqlCmd = "SELECT * FROM User WHERE Username = " + username
                + " AND Password = " + password;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount; //Number of rows in the cursor
    }

    public User getUserData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        username = "'" + username.toLowerCase() + "'";

        String SqlCmd = "SELECT * FROM User WHERE Username = " + username;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        User user = new User();

        if (cursor.moveToFirst()) {
            do {
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setDob(cursor.getString(3));
                user.setAddress(cursor.getString(4));
                user.setPhone(cursor.getString(5));
                user.setEmail(cursor.getString(6));
                user.setAdmin(cursor.getInt(7));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return user;
    }

    public ArrayList<User> getAllUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM User";
        Cursor cursor = db.rawQuery(SqlCmd, null);
        ArrayList<User> userArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setDob(cursor.getString(3));
                user.setAddress(cursor.getString(4));
                user.setPhone(cursor.getString(5));
                user.setEmail(cursor.getString(6));
                user.setAdmin(cursor.getInt(7));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return userArrayList;
    }

    public User getUserDetailDataByEmail(String email) {
        email = "'" + email + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        String SqlCmd = "SELECT * FROM User WHERE Email = " + email;
        Cursor cursor = db.rawQuery(SqlCmd, null);
        User user = new User();

        if (cursor.moveToFirst()) {
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setDob(cursor.getString(3));
            user.setAddress(cursor.getString(4));
            user.setPhone(cursor.getString(5));
            user.setEmail(cursor.getString(6));
            user.setAdmin(cursor.getInt(7));
        }

        cursor.close();
        return user;
    }

    public boolean addUserData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = true;
        String username, password, name, dob, email, phone, address;
        int admin;

        username = "'" + user.getUsername() + "'";
        password = "'" + user.getPassword() + "'";
        name = "'" + user.getName() + "'";
        dob = "'" + user.getDob() + "'";
        email = "'" + user.getEmail() + "'";
        phone = "'" + user.getPhone() + "'";
        address = "'" + user.getAddress() + "'";
        admin = 0;

        String sqlCmd = "INSERT INTO User VALUES(" + username
                + "," + password + "," + name + "," + dob
                + "," + address + "," + phone + "," + email + "," + admin + ")";

        try {
            db.execSQL(sqlCmd);
        } catch (Exception ex) {
            result = false;
        }

        return result;
    }

    public boolean addTestDriveRegisterData(TestDrive testDriveData) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = true;
        String name, address, phone, email, registerDate, testProduct;

        name = "'" + testDriveData.getName() + "'";
        address = "'" + testDriveData.getAddress() + "'";
        phone = "'" + testDriveData.getPhone() + "'";
        email = "'" + testDriveData.getEmail() + "'";
        registerDate = "'" + testDriveData.getRegisterDate() + "'";
        testProduct = "'" + testDriveData.getTestProduct() + "'";

        String sqlCmd = "INSERT INTO TestDrive VALUES(" + name
                + "," + address + "," + phone
                + "," + email + "," + registerDate + "," + testProduct + ")";

        try {
            db.execSQL(sqlCmd);
        } catch (Exception ex) {
            result = false;
        }

        return result;
    }
    //endregion
}