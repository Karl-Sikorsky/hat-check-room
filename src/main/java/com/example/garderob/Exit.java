package com.example.garderob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ПОДАРУНКОВИЙ on 21.11.2016.
 */
public class Exit extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_exit);

    }

    public void onClickExit(View view) {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
    }
}