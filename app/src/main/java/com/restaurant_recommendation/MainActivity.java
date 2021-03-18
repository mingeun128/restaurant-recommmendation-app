package com.restaurant_recommendation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.PrivilegedExceptionAction;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    ConstraintLayout.LayoutParams layoutParams;
    Animation slideRight;
    Animation slideLeft;
    Animation blink;
    ImageView testImage1;
    ImageView testImage2;
    ImageView redScreen;
    ImageView greenScreen;
    FloatingActionButton goodButton;
    FloatingActionButton badButton;

    //only test
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        addImageView(testImage2, R.drawable.pizza_test);
    }
    private void init(){
        //set parent layout
        constraintLayout = findViewById(R.id.main_layout);
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        //set anim
        slideRight = AnimationUtils.loadAnimation(this,R.anim.slide_away_right);
        slideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_away_left);
        blink = AnimationUtils.loadAnimation(this,R.anim.blink);
        blink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(greenScreen.getVisibility() == View.VISIBLE){
                    greenScreen.setVisibility(View.INVISIBLE);
                }
                if(redScreen.getVisibility() == View.VISIBLE){
                    redScreen.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //set like/dislike screen
        redScreen = findViewById(R.id.red_img);
        greenScreen = findViewById(R.id.green_img);

        //set buttons
        goodButton = findViewById(R.id.good_button);
        badButton = findViewById(R.id.bad_button);

    }
    private void addImageView(ImageView img, int id){
        img = new ImageView(this);
        img.setImageResource(id);
        img.setLayoutParams(layoutParams);
        constraintLayout.addView(img);
        setSwipeEvent(img);
        setButtonEvent(img);
    }
    private void setSwipeEvent(ImageView img){
        img.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            public void onSwipeRight() {
                imageSlide(img,false);
            }
            public void onSwipeLeft() {
                imageSlide(img,true);
            }
        });
    }
    private void setButtonEvent(ImageView img){
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSlide(img,false);
            }
        });
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSlide(img,true);
            }
        });
    }
    private void imageSlide(ImageView img, Boolean isLeft){
        if(isLeft){
            img.startAnimation(slideLeft);
            redScreen.setVisibility(View.VISIBLE);
            redScreen.startAnimation(blink);
        }
        else{
            img.startAnimation(slideRight);
            greenScreen.setVisibility(View.VISIBLE);
            greenScreen.startAnimation(blink);
        }
        constraintLayout.removeView(img);
        if(count%2 == 0){
            addImageView(testImage1, R.drawable.blueberries_test);
        }
        else{
            addImageView(testImage2, R.drawable.pizza_test);
        }
        count++;
    }
}