package com.example.samplecollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.util.MyMaker;

import java.util.ArrayList;
import java.util.List;

@MyMaker(classify = "", memo="RecyclerActivity")
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initLayout();
        initData();
    }

    /**
     * 레이아웃 초기화
     */
    private void initLayout(){

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }

    /**
     * 데이터 초기화
     */
    private void initData(){

        List<Album> albumList = new ArrayList<Album>();

        for (int i =0; i<20; i ++){

            Album album = new Album();
            album.setTitle("어느 멋진 날");
            album.setArtist("정용");
            album.setImage(R.drawable.ic_launcher_background);
            albumList.add(album);
        }

        recyclerView.setAdapter(new MyRecyclerAdapter(albumList, R.layout.row_album));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}


