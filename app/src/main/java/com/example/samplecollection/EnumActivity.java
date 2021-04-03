package com.example.samplecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.util.MyMaker;

@MyMaker(classify = "Enum", memo="Enum Activity 입니다.")
public class EnumActivity extends AppCompatActivity {
    public enum GameState {
        GAME_STATE_LEFT,
        GAME_STATE_RIGHT,
    };

    public enum HandlerMessage {
        HANDLER_MESSAGE_DRAW(1),
        HANDLER_MESSAGE_SEND(2);

        HandlerMessage(int value) {
            this.value = value;
        }
        private final int value;
        public int getValue() {
            return value;
        }
    };

    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enum);
        GameState gameState = GameState.GAME_STATE_LEFT;
        Log.d("kihoon.kim", "gameState = " + gameState);
        Log.d("kihoon.kim", "HandlerMessage.HANDLER_MESSAGE_SEND.getValue() = " + HandlerMessage.HANDLER_MESSAGE_SEND.getValue());

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == HandlerMessage.HANDLER_MESSAGE_DRAW.getValue() ) {
                    mHandler.sendEmptyMessageDelayed(msg.what, 2000);
                    Log.d("kihoon.kim", "System.currentTimeMillis() = " + System.currentTimeMillis());
                    Log.d("kihoon.kim", "HANDLER_MESSAGE_DRAW");
                } else if(msg.what == HandlerMessage.HANDLER_MESSAGE_SEND.getValue() ) {
                    Log.d("kihoon.kim", "HANDLER_MESSAGE_SEND");
                }
                return true;
            }
        });

        Message message = mHandler.obtainMessage();
        message.what = HandlerMessage.HANDLER_MESSAGE_SEND.getValue();

        mHandler.sendMessage(message);

        message = mHandler.obtainMessage();
        message.what = HandlerMessage.HANDLER_MESSAGE_DRAW.getValue();

        mHandler.sendEmptyMessageDelayed(message.what, 2000);
    }
}