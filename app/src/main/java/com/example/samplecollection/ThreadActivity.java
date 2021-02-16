package com.example.samplecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.MyMaker;

@MyMaker(classify = "", memo="ThreadActivity")

public class ThreadActivity extends AppCompatActivity {
    private Button mButton1;
    private Button mButton2;
    private TextView mTextView;
    private int mCount;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mCount++;
            mTextView.setText(""+mCount);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(), 10);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        mCount = 0;
        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kihoon.kim", "Hello");
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        for(int i=0;i<10000;i++) {
                            for(int j=0;j<10000;j++) {
                                Log.d("kihoon.kim", "tick");
                            }
                        }
                    }
                };
                thread.start();

/*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Log.d("kihoon.kim", "World");
            }
        });

        mButton2 = findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kihoon.kim", "getString() 0");
                getString(new IStringListener() {
                    @Override
                    public void onGetString(String retStr) {
                        Log.d("kihoon.kim", "getString() 5");
                        Log.d("kihoon.kim", "getString() retStr ==6" + retStr);
                        //Toast.makeText(getBaseContext(), retStr, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("kihoon.kim", "getString() 1");
            }
        });

        mTextView = findViewById(R.id.textview);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(), 10);
    }

    interface IStringListener {
        void onGetString(String retStr);
    }

    private void getString(IStringListener stringListener) {
        Log.d("kihoon.kim", "getString() 2");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Log.d("kihoon.kim", "getString() 3");

                    Thread.sleep(1000);
                    Log.d("kihoon.kim", "getString() 4");
                    stringListener.onGetString("hosung");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        Log.d("kihoon.kim", "getString() 2");
    }
}