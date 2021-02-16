package com.example.samplecollection;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class ExamDB {
    private static final String AUTHORITY = "com.example.samplecollection.ExamProvider";

    public static final class Data {
        public static final String TABLE_NAME = "Exam";
        public static final Uri        	CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
        public static final String     	COLUMN_NAME = "name";
        public static final String     	COLUMN_VALUE = "value";

        public static final String COLUMN_NAME_X = "X";
        public static final String COLUMN_NAME_Y = "Y";
        private static final String    	DATA_SELECTION = COLUMN_NAME + "=?";


        public static String getString(ContentResolver cr, String name) {
            Cursor c = null;
            try {
                c = cr.query(CONTENT_URI, new String[] { COLUMN_VALUE }, DATA_SELECTION, new String[] { name }, null);
                if ( c != null && c.moveToNext() ) {
                    return c.getString(0);
                }
            } finally {
                if ( c != null ) {
                    c.close();
                    c = null;
                }
            }
            // if there is no field return "";
            return "";
        }

        public static boolean putString(ContentResolver cr, String name, String value) throws IllegalArgumentException {
            Cursor c = null;
            ContentValues values = new ContentValues();
            try {
                c = cr.query(CONTENT_URI, new String[] { COLUMN_VALUE }, DATA_SELECTION, new String[] { name }, null);
                if ( c != null && c.moveToNext() ) {
                    values.put(COLUMN_VALUE, value);
                    int i = cr.update(CONTENT_URI, values, DATA_SELECTION, new String[] { name });

                    if ( i == -1 ) {
                        throw new IllegalArgumentException();
                    }
                    return true;
                } else {
                    values.put(COLUMN_NAME, name);
                    values.put(COLUMN_VALUE, value);
                    cr.insert(CONTENT_URI, values);
                    return true;
                }
            } finally {
                if ( c != null ) {
                    c.close();
                    c = null;
                }
            }
        }

        public static int getInt(ContentResolver cr, String name) {
            String v = getString(cr, name);
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            }
        }

        public static boolean putInt(ContentResolver cr, String name, int value) {
            return putString(cr, name, Integer.toString(value));
        }

        public static boolean getBoolean(ContentResolver cr, String name) {
            String v = getString(cr, name);
            return Boolean.parseBoolean(v);
        }

        public static boolean putBoolean(ContentResolver cr, String name, boolean value) {
            Log.d("kihoon.kim", "putBoolean="+value);
            return putString(cr, name, Boolean.toString(value));
        }

    }
}
