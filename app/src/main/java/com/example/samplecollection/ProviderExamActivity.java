package com.example.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.util.MyMaker;

@MyMaker(classify = "Preference, Content Provider", memo="Preference, Content Provider 저장 예제")
public class ProviderExamActivity extends AppCompatActivity {

    private Button mBtnPreferenceAdd;
    private Button mBtnPreferenceRead;
    private Button mBtnPreferenceDelete;
    private Button mBtnDBAdd;
    private Button mBtnDBRead;
    private Button mBtnDBDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_exam);

        mBtnPreferenceAdd = findViewById(R.id.add_preference_button);
        mBtnPreferenceRead = findViewById(R.id.read_preference_button);
        mBtnPreferenceDelete = findViewById(R.id.delete_preference_button);

        mBtnDBAdd = findViewById(R.id.add_button);
        mBtnDBRead = findViewById(R.id.read_button);
        mBtnDBDelete = findViewById(R.id.delete_button);

        mBtnPreferenceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Score", "100");
                editor.commit();

                Toast.makeText(getBaseContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnPreferenceRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String value = pref.getString("Score", "0");
                Toast.makeText(getBaseContext(), "value = " + value, Toast.LENGTH_SHORT).show();
            }
        });

        mBtnPreferenceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("Score");
                editor.commit();
                Toast.makeText(getBaseContext(), "값을 지웠습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        mBtnDBAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamDB.Data.putInt(getBaseContext().getContentResolver(), ExamDB.Data.COLUMN_NAME_X, 20);
                Toast.makeText(getBaseContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnDBRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = ExamDB.Data.getInt(getBaseContext().getContentResolver(), ExamDB.Data.COLUMN_NAME_X);
                Toast.makeText(getBaseContext(), "x = " + x, Toast.LENGTH_SHORT).show();
            }
        });

        mBtnDBDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}