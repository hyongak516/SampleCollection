package com.example.samplecollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by woong on 2015. 1. 20..
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Album> albumList;
    private int itemLayout;

    /**
     * 생성자
     *
     * @param items
     * @param itemLayout
     */
    public MyRecyclerAdapter(List<Album> items, int itemLayout) {

        this.albumList = items;
        this.itemLayout = itemLayout;
    }

    /**
     * 레이아웃을 만들어서 Holer에 저장
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Album item = albumList.get(position);
        viewHolder.textTitle.setText(item.getTitle());
        viewHolder.img.setBackgroundResource(item.getImage());
        viewHolder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        }

    }
}