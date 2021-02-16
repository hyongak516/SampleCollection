package com.example.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.util.MyMaker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

class Movie {
    int movieId;
    String title;
    String image;
    String director;
    int year;

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                '}';
    }
}
@MyMaker(classify = "", memo="RestAPIActivity DB")
public class RestAPIActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        ButterKnife.bind(this);
        //mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestFactory.requestRestApi(new URL("http://218.148.137.189:3000/movies/"), null, "POST", new IResult() {
                        @Override
                        public void completion(int responseCode, JSONObject jsonObject) throws JSONException {
                            if(responseCode== HttpURLConnection.HTTP_OK) {
                                int count = jsonObject.getInt("count");
                                Log.d("kihoon.kim", "count = " + count);
                                JSONArray jsonArray = jsonObject.getJSONArray("movies");
                                int index = 0;
                                while(index < jsonArray.length()) {
                                    //Log.d("kihoon.kim", "jsonArray = " + jsonArray.get(index));
                                    String json = jsonArray.get(index).toString();
                                    Gson gson = new Gson();
                                    Movie movie = gson.fromJson(json,  Movie.class);
                                    Log.d("kihoon.kim", "movie = " + movie);
                                    index ++;
                                }
                            }
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
    }
}