package com.example.samplecollection;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class PhoneBookProvider extends ContentProvider {
    private static final String         TAG             = "PhoneBookProvider";
    private static final String 		DATABASE_NAME 	= "phonebook.db";
    private SystemUIProviderOpenHelper 	mOpenHelper;
    private static SQLiteDatabase 		mDB;

    @Override
    public boolean onCreate() {
        mOpenHelper 	= new SystemUIProviderOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
        String 	strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getReadableDatabase();
        Cursor c = mDB.query(strTable, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String arg1, String[] arg2) {
        String 	strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getWritableDatabase();
        int rowID = mDB.delete(strTable, arg1, arg2);
        Uri returi = ContentUris.withAppendedId(uri, rowID);
        getContext().getContentResolver().notifyChange(returi, null);
        return rowID;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long 	rowID;
        String 	strTable = uri.getPathSegments().get(0);
        mDB 	= mOpenHelper.getWritableDatabase();
        rowID	= mDB.insert(strTable, null, values);
        if (rowID <= 0)
            return null;

        Uri returi=ContentUris.withAppendedId(uri, rowID);
        getContext().getContentResolver().notifyChange(uri, null);
        return returi;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String 	strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getWritableDatabase();
        int rowID = mDB.update(strTable, values, selection,selectionArgs);
        Uri returi = ContentUris.withAppendedId(uri, rowID);
        getContext().getContentResolver().notifyChange(returi, null);
        return rowID;
    }

    class SystemUIProviderOpenHelper extends SQLiteOpenHelper {
        private static final int	 	VERSION 		= 1;
        private static final String	 	TABLE_NAME 		= "phonebook";

        public SystemUIProviderOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
            Log.w(TAG, "SystemUIProviderOpenHelper()");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, "onCreate DB Version = "+db.getVersion());
            Cursor c1=db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+TABLE_NAME+"'", null);
            try {
                if (c1.getCount()==0) {
                    db.execSQL("CREATE TABLE "+TABLE_NAME+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, value TEXT);");
                    //db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null,'kihoon.kim','010-8327-2859');");
                    //db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null,'ran.jeong','010-3321-6816');");
                }
            } finally {
                c1.close();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database - current DB Version = "+db.getVersion()+" , oldVersion = "+oldVersion+" , newVersion = "+newVersion);
        }
    }
}