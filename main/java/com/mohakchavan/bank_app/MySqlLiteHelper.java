package com.mohakchavan.bank_app;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

/**
 * Created by Mohak Chavan on 16-10-2017.
 */

public class MySqlLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db",
            USER_ID = "id",
            TABLE_USERS = "User",
            USER_NAME = "Name",
            USER_SURNAME = "Surname",
            USER_AMOUNT = "Amount",
            USER_PASS = "PASS";
    private UserDetailModel udm;

    String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USERS + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT,"
            + USER_SURNAME + " TEXT," + USER_AMOUNT + " INTEGER," + USER_PASS + " INTEGER" + ")";

    public MySqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public boolean addUser(final String name, final String sname, double amt) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues cv = new ContentValues();
        int random = new Random().nextInt(300);
        cv.put(USER_NAME, name);
        cv.put(USER_SURNAME, sname);
        cv.put(USER_AMOUNT, amt);
        cv.put(USER_PASS, random);
        int i = (int) db.insert(TABLE_USERS, null, cv);
        db.close();
        return i > 0;
    }

    public UserDetailModel getDataByName(final String name) {
        udm=new UserDetailModel();
        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME, USER_SURNAME, USER_AMOUNT, USER_PASS}, USER_NAME + "=?",
                new String[]{name}, null, null, USER_ID);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            udm.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
            udm.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
            udm.setSname(cursor.getString(cursor.getColumnIndex(USER_SURNAME)));
            udm.setAmt((cursor.getDouble(cursor.getColumnIndex(USER_AMOUNT))));
            udm.setPass(cursor.getInt(cursor.getColumnIndex(USER_PASS)));
        }
        return udm;
    }

    public boolean isValid(final int id, final int pass) {
        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(TABLE_USERS, new String[]{USER_PASS}, USER_ID + "=?", new String[]{Integer.toString(id)}, null, null, USER_ID);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                if (pass == cursor.getInt(cursor.getColumnIndex(USER_PASS)))
                    return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String viewAll() {
        final SQLiteDatabase db = getWritableDatabase();
        String alldata = "";
        final Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME, USER_SURNAME, USER_AMOUNT, USER_PASS},
                String.valueOf(1), null, null, null, USER_ID);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                alldata = alldata + cursor.getInt(cursor.getColumnIndex(USER_ID)) + " " + cursor.getString(cursor.getColumnIndex(USER_NAME))
                        + " " + cursor.getString(cursor.getColumnIndex(USER_SURNAME)) + " " + cursor.getDouble(cursor.getColumnIndex(USER_AMOUNT))
                        + " " + cursor.getInt(cursor.getColumnIndex(USER_PASS)) + "\n";
            } while (cursor.moveToNext());
            return alldata;
        }
        return null;
    }

    public UserDetailModel getDataByAccNo(final int accno) {
        udm = new UserDetailModel();
        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME, USER_SURNAME, USER_AMOUNT, USER_PASS}, USER_ID + "=?",
                new String[]{String.valueOf(accno)}, null, null, USER_ID);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            udm.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
            udm.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
            udm.setSname(cursor.getString(cursor.getColumnIndex(USER_SURNAME)));
            udm.setAmt((cursor.getDouble(cursor.getColumnIndex(USER_AMOUNT))));
            udm.setPass(cursor.getInt(cursor.getColumnIndex(USER_PASS)));
        }
        return udm;
    }
}
