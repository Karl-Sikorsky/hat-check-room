package com.example.garderob;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Random;


public class Token extends FrameLayout implements OnTouchListener {
    private TextView tokenText;
    public Token(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Token(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Token(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater li = ((Activity) getContext()).getLayoutInflater();
        // загружаем в себя (мы унаследовались от FrameLayout) содержимое
        // файла-разметки token_layout
        li.inflate(R.layout.token_layout, this);

       tokenText = (TextView)findViewById(R.id.token_text);
        spawn();



    }
    public void spawn(){
        tokenText.setText(String.valueOf(setNumb()));
    }
    public int setNumb(){
        Random rand = new Random();
        int i= rand.nextInt(9000)+1000;
   return i;
    }
    public int getNumb(){
        return Integer.parseInt(tokenText.getText().toString());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
