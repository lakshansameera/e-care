
package com.example.Sameera.hearts.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static volatile SQLiteHandler databaseHelper;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eCare";

    // table names
    private static final String TABLE_CUSTOMER = "tbl_customer";
    private static final String TABLE_NEW_CUSTOMER = "tbl_new_customer";
    private static final String TABLE_ADDRESS = "tbl_address";

    // customer Table Columns names
    private static final String KEY_ID = "cus_id";
    private static final String KEY_NAME = "cus_name";
    private static final String KEY_CODE = "cus_code";
    private static final String KEY_ADDRESS = "cus_address";

    // new customer Table Columns names
    private static final String KEY_NEW_ID = "new_cus_id";
    private static final String KEY_NEW_NAME = "new_name";
    private static final String KEY_NEW_CODE = "new_code";
    private static final String KEY_NEW_ADDRESS = "new_address";
    private static final String KEY_NEW_STATUS = "status";

    //hechrac

    // new address Table Columns names
    private static final String KEY_ADD_ID = "add_id";
    private static final String KEY_ADD_NAME = "add_name";
    private static final String KEY_CUS_ID = "new_cus_id";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteHandler getDatabaseInstance(@NotNull Context context) {
        if (databaseHelper == null) {
            databaseHelper = new SQLiteHandler(context);
        }
        return databaseHelper;
    }

    // Creating Tables
    /*
    Table for customer
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_ADDRESS + " TEXT"
                + ")";
        db.execSQL(CREATE_CUSTOMER_TABLE);

 /*
    Table for register customer
     */
        String CREATE_NEW_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_NEW_CUSTOMER + "("
                + KEY_NEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NEW_NAME + " TEXT,"
                + KEY_NEW_CODE + " TEXT,"
                + KEY_NEW_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_NEW_CUSTOMER_TABLE);

         /*
    Table for customer address
     */
        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + "("
                + KEY_ADD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ADD_NAME + " TEXT,"
                + KEY_CUS_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + KEY_CUS_ID + ") REFERENCES " + TABLE_NEW_CUSTOMER + " (" + KEY_NEW_ID + ")"
                + ")";
        db.execSQL(CREATE_ADDRESS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing customer details in database
     */
    public void addcustomer(int cid, String name, String code, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, cid); // Id
            values.put(KEY_NAME, name); // Name
            values.put(KEY_CODE, code); // Code
            values.put(KEY_ADDRESS, address); // Address


            // Inserting Row
            long id = db.insert(TABLE_CUSTOMER, null, values);
            Log.d(TAG, "Customer inserted into sqlite: " + id);

            db.setTransactionSuccessful();

        } finally {

            db.endTransaction();

        }


//        db.close(); // Closing database connection


    }

    public void addnewcustomer(String name, String code, ArrayList address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NEW_NAME, name); // Name
        values.put(KEY_NEW_CODE, code); // Code
        values.put(KEY_NEW_STATUS, 0); // status


        // Inserting Row
        long id = db.insert(TABLE_NEW_CUSTOMER, null, values);

        String sql = "SELECT seq FROM sqlite_sequence WHERE name = 'tbl_new_customer'";

        Cursor cursor = db.rawQuery(sql, null);
        int customer_id = 0;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            customer_id = cursor.getInt(0);

        }
        cursor.close();

        for (int i = 0; i < address.size(); i++) {
            ContentValues value = new ContentValues();
            value.put(KEY_ADD_NAME, address.get(i).toString()); // Name
            value.put(KEY_CUS_ID, customer_id); // id


            db.insert(TABLE_ADDRESS, null, value);
        }


        db.close(); // Closing database connection


        Log.d(TAG, "New customer inserted into sqlite: " + id);
    }

    //    Update customer data
    public void updateCustomer(String name, String code, String address, String cid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NEW_NAME, name); // Name
        values.put(KEY_NEW_CODE, code); // Code


        // Inserting Row
        long id = db.update(TABLE_CUSTOMER, values, KEY_ID + " = " + cid, null);


        db.close(); // Closing database connection


        Log.d(TAG, "Customer updated into sqlite: " + cid);
    }

    /**
     * Getting customer data from database
     */

//    public static ArrayList<Customer> getCustomers(Context context) {
//
//        SQLiteHandler databaseHelper = SQLiteHandler.getDatabaseInstance(context);
//        try {
//            SQLiteDatabase database = databaseHelper.getWritableDatabase();
//            String customerQuery = "select id, name, phone, code from customer";
//
//            Object[] objects = {};
//            Cursor customerCursor = DbHandler.performRawQuery(database, customerQuery, objects);
//            ArrayList<Customer> customers = new ArrayList<Customer>();
//            for (customerCursor.moveToFirst(); !customerCursor.isAfterLast(); customerCursor.moveToNext()) {
//                Customer customer = new Customer(
//                        customerCursor.getInt(0),
//                        customerCursor.getString(1),
//                        customerCursor.getString(2),
//                        customerCursor.getString(3)
//
//                );
//
//
//                customers.add(customer);
//
//            }
//
//            customerCursor.close();
//            return customers;
//        } finally {
//            databaseHelper.close();
//
//
//        }
//    }


    public HashMap<String, String> getCustomerDetails() {
        HashMap<String, String> customer = new HashMap<String, String>();
        String selectQuery = "SELECT id, name, code, phone FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            customer.put("id", cursor.getString(1));
            customer.put("name", cursor.getString(2));
            customer.put("code", cursor.getString(3));
            customer.put("phone", cursor.getString(4));

        }
        cursor.close();
        db.close();
        // return customer
        Log.d(TAG, "Fetching customers from Sqlite: " + customer.toString());

        return customer;
    }


    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteCustomers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CUSTOMER, null, null);
        db.close();

        Log.d(TAG, "Deleted all customers info from sqlite");
    }

}
