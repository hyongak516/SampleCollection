package com.example.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.util.MyMaker;
@MyMaker(classify = "Template", memo="Template Activity 입니다.")
public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
    }
}