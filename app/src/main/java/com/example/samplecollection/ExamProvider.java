package com.example.samplecollection;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class ExamProvider extends ContentProvider {
    private static final String         TAG                = "ExamProvider";
    private static final String         DATABASE_NAME      = "samplecollection.db";

    private QDebugProviderOpenHelper  	mOpenHelper;
    private static SQLiteDatabase       mDB;

    @Override
    public boolean onCreate() {
        mOpenHelper     = new QDebugProviderOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
        String     strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getReadableDatabase();
        Cursor c = mDB.query(strTable, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String arg1, String[] arg2) {
        String     strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getWritableDatabase();
        int rowID = mDB.delete(strTable, arg1, arg2);

        //String num = uri.getPathSegments().get(2);
        //Uri returi=ContentUris.withAppendedId(uri, Integer.parseInt(num));
        getContext().getContentResolver().notifyChange(uri, null);
        return rowID;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long     rowID;
        String     strTable = uri.getPathSegments().get(0);
        mDB     = mOpenHelper.getWritableDatabase();
        rowID    = mDB.insert(strTable, null, values);
        if (rowID <= 0)
            return null;

        //Uri returi=ContentUris.withAppendedId(uri, rowID);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int     rowID;
        String     strTable = uri.getPathSegments().get(0);
        mDB = mOpenHelper.getWritableDatabase();
        rowID = mDB.update(strTable, values, selection,selectionArgs);

        //String num = uri.getPathSegments().get(2);
        //Uri returi=ContentUris.withAppendedId(uri, Integer.parseInt(num));
        getContext().getContentResolver().notifyChange(uri, null);
        return rowID;
    }

    class QDebugProviderOpenHelper extends SQLiteOpenHelper {
        private static final int         VERSION                         = 1;
        private Context mContext;
        public QDebugProviderOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
            mContext = context;
            Log.w(TAG, "QDebugProviderOpenHelper()");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, "onCreate DB Version = "+db.getVersion());
            WindowManager manager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            Display disp = manager.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);

            Cursor c1 = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ExamDB.Data.TABLE_NAME  + "'", null);
            try {
                if ( c1.getCount() == 0 ) {
                    db.execSQL("CREATE TABLE " + ExamDB.Data.TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, value TEXT);");
                    db.execSQL("INSERT INTO " + ExamDB.Data.TABLE_NAME + " VALUES(null,'" + ExamDB.Data.COLUMN_NAME_X + "','" + "0" + "');");
                    db.execSQL("INSERT INTO " + ExamDB.Data.TABLE_NAME + " VALUES(null,'" + ExamDB.Data.COLUMN_NAME_Y + "','" + "0" + "');");
                }
            } finally {
                c1.close();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database - current DB Version = "+db.getVersion()+" , oldVersion = "+oldVersion+" , newVersion = "+newVersion);
            if ( newVersion < 100 ) {
                db.execSQL("DROP TABLE IF EXISTS " + ExamDB.Data.TABLE_NAME + ";");
                onCreate(db);
            }
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if ( newVersion < 100 ) {
                db.execSQL("DROP TABLE IF EXISTS " + ExamDB.Data.TABLE_NAME + ";");
                onCreate(db);
            }
        }
    }
}
