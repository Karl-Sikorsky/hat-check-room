package com.example.garderob;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static java.lang.Math.abs;

public class MainActivity extends Activity implements View.OnTouchListener {

    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;
    private int chooseToken =1;
    private int capturedNumb = 0;
    Boolean touchFlag = false;
    boolean dropFlag = false;
    LayoutParams imageParams,startedParams;
    ImageView imageDrop;


    int eX, eY;
    int scorePoint = 0,neededPoint = 10,level=1,tolik=5,nike=5;
    int topY, leftX, rightX, bottomY;
    long startTime = System.currentTimeMillis();
    Token[] tokens;
    TextView time,score,needed,tolikView,nikeView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        View root = findViewById(android.R.id.content).getRootView();
        imageDrop = (ImageView) findViewById(R.id.ImgDrop);
        score = (TextView)  findViewById(R.id.textView);
        time = (TextView) findViewById(R.id.textView2);
        needed = (TextView) findViewById(R.id.textView3);
        tolikView = (TextView)findViewById(R.id.textViewTolik);
        nikeView = (TextView)findViewById(R.id.textViewNike);

        tokens = new Token[4];
        tokens[0] = (Token) findViewById(R.id.token1);
        tokens[1] = (Token) findViewById(R.id.token2);
        tokens[2] = (Token) findViewById(R.id.token3);
        tokens[3] = (Token) findViewById(R.id.token4);
        for(int i=0;i<4;i++){
            tokens[i].setOnTouchListener(this);
        }






        root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (touchFlag) {
                    System.err.println("Display If  Part ::->" + touchFlag);
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            topY = imageDrop.getTop();
                            leftX = imageDrop.getLeft();
                            rightX = imageDrop.getRight();
                            bottomY = imageDrop.getBottom();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            eX = (int) event.getX();
                            eY = (int) event.getY();
                            int x = (int) event.getX() - offset_x;
                            int y = (int) event.getY() - offset_y;
                            int w = getWindowManager().getDefaultDisplay().getWidth() - 50;
                            int h = getWindowManager().getDefaultDisplay().getHeight() - 10;
                            if (x > w) x = w;
                            if (y > h) y = h;
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                            lp.setMargins(x, y, 0, 0);


                            selected_item.setLayoutParams(lp);
                            break;
                        case MotionEvent.ACTION_UP:
                            if ((eX > leftX && eX < rightX && eY > topY && eY < bottomY)&&(abs(capturedNumb-((eX-leftX)*(9000/(rightX-leftX))))<1000)) {
                                imageDrop.setBackgroundColor(Color.argb(50,140,255,140));
                                tokens[chooseToken-1].spawn();

                                selected_item.bringToFront();
                                dropFlag = true;
                                selected_item.setLayoutParams(startedParams);

                                minusNeeded();
                            } else {
                                imageDrop.setBackgroundColor(Color.argb(50,255,140,140));
                               selected_item.setLayoutParams(startedParams);
                                plusPoint();
                            }
                            touchFlag = false;

                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });
    }

    private void minusNeeded() {
        long currentTime = System.currentTimeMillis();
        neededPoint--;
        needed.setText("Осталось: "+neededPoint);
        tolik--;
        nike=5;
        nikeView.setText(String.valueOf(nike));
        if(tolik==0){
            tolik=5;
            scorePoint=scorePoint-5;
        }
        tolikView.setText(String.valueOf(tolik));
        time.setText("время: "+(((currentTime-startTime)/1000))+" сек\n уровень: "+level);
        if (neededPoint==0)levelUp();


    }

    private void levelUp() {
        level++;
        neededPoint=10+(level-1)*5;
        scorePoint=0;
         startTime = System.currentTimeMillis();
        for(int i=0;i<4;i++)tokens[i].spawn();
    }

    private void plusPoint() {
        long currentTime = System.currentTimeMillis();
        scorePoint=scorePoint+level;
        score.setText("проебано: "+scorePoint);
        time.setText("время: "+(((currentTime-startTime)/1000))+ "сек\n уровень: "+level);

        tolik=5;
        tolikView.setText(String.valueOf(tolik));
        nike--;
        if(nike==0){
            nike=5;
            scorePoint=scorePoint+5;
        }
        nikeView.setText(String.valueOf(nike));
        if((scorePoint>40)||(((currentTime-startTime)/1000))>100){
            gameOver();
        }
    }

    private void gameOver() {
        Intent intentExit = new Intent(this,Exit.class);
        startActivity(intentExit);

    }

    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = true;
                offset_x = (int) event.getX();
                offset_y = (int) event.getY();
                selected_item = v;
                imageParams = v.getLayoutParams();
                startedParams = v.getLayoutParams();
              if (v.getId()==R.id.token1){capturedNumb=tokens[0].getNumb(); chooseToken=1;}
                if (v.getId()==R.id.token2){capturedNumb=tokens[1].getNumb(); chooseToken=2;}
                if (v.getId()==R.id.token3){capturedNumb=tokens[2].getNumb(); chooseToken=3;}
                if (v.getId()==R.id.token4){capturedNumb=tokens[3].getNumb(); chooseToken=4;}

                break;
            case MotionEvent.ACTION_UP:
                selected_item = null;
                touchFlag = false;
                break;
            default:
                break;
        }
        return false;
    }
}
