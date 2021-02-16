package com.example.samplecollection;
import com.example.util.MyMaker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@MyMaker(classify = "", memo="PhoneBook DB")
public class PhoneBookDBActivity extends Activity {
    private Button 				mButton01;
    private Button 				mButton02;
    private Button 				mButton03;
    private Button 				mButton04;

    private static final 	Uri 	CONTENT_URI = Uri.parse("content://com.example.samplecollection.phonebookprovider/phonebook");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonebookdb_layout);

        mButton01 = (Button) findViewById(R.id.button01);
        mButton01.setText("show kihoon.kim");
        mButton01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = null;
                // kihoon.kim 을 가져올때.
                //c = getBaseContext().getContentResolver().query(CONTENT_URI, new String[] {"name", "value"}, "name=?",new String[] {"kihoon.kim"},null);
                // 모두 가져올때
                //c = getBaseContext().getContentResolver().query(CONTENT_URI, new String[] {"name", "value"}, null, null,null);
                if (c.moveToFirst()) {
                    String name = c.getString(0);
                    String value = c.getString(1);
                    Toast.makeText(getBaseContext(), "name = "+name+", value = "+value, Toast.LENGTH_LONG).show();
                }

                c.moveToNext();
                String name = c.getString(0);
                String value = c.getString(1);
                Toast.makeText(getBaseContext(), "name = "+name+", value = "+value, Toast.LENGTH_LONG).show();
                c.close();
            }
        });

        mButton02 = (Button) findViewById(R.id.button02);
        mButton02.setText("set kihoon.kim +1");
        mButton02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("value", "123456789");
                getContentResolver().update(CONTENT_URI, values, "name='kihoon.kim'", null);
                //Toast.makeText(getBaseContext(), "Music = true", Toast.LENGTH_LONG).show();
            }
        });

        mButton03 = (Button) findViewById(R.id.button03);
        mButton03.setText("set kihoon.kim -1");
        mButton03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("value", "987654321");
                getContentResolver().update(CONTENT_URI, values, "name='kihoon.kim'", null);
                //Toast.makeText(getBaseContext(), "Music = false", Toast.LENGTH_LONG).show();
            }
        });

        mButton04 = (Button) findViewById(R.id.button04);
        mButton04.setText("observer");
        mButton04.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "registerContentObserver()", Toast.LENGTH_LONG).show();
                ContentResolver cr = getContentResolver();
                cr.registerContentObserver(CONTENT_URI , true, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Toast.makeText(getBaseContext(), "onChange()", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}