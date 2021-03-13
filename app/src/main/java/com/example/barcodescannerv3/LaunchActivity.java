package com.example.barcodescannerv3;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import lombok.SneakyThrows;

public class LaunchActivity extends AppCompatActivity {
    private ImageView circle;
    private FloatingActionButton fab;
    private ProgressBar pb;
    private ImageView done;
    private Button next_button;
    private EditText ip;
    private RESTMethods rest = new RESTMethods();
    private BooleanViewModel model_bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        circle = findViewById(R.id.circle);
        fab = findViewById(R.id.fab);
        pb = findViewById(R.id.progressBar2);
        done = findViewById(R.id.done);
        next_button = findViewById(R.id.next_button);
        ip = findViewById(R.id.ip_editText);
        model_bool = new ViewModelProvider(this).get(BooleanViewModel.class);

        final Observer<Boolean> boolObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean.equals(true)){
                    validIp();
                } else {
                    badIp();
                }
            }
        };

        model_bool.getMutableBoolean().observe(this,boolObserver);

        fab.setOnClickListener(v -> pingBtnClicked());
        next_button.setOnClickListener(v -> nextBtnClicked());
    }

    private void nextBtnClicked() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("ip",ip.getText().toString());
        startActivity(intent);
    }

    private void pingBtnClicked() {
        fab.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        try {
            rest.ping(ip.getText().toString(),model_bool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validIp() {
        circle.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        pb.setVisibility(View.INVISIBLE);
        ip.setEnabled(false);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(circle, "translationX", 400f);
        anim1.setDuration(1000);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(done, "translationX", 400f);
        anim2.setDuration(1000);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(next_button, "alpha", 1f);
        anim3.setDuration(1000);
        anim3.setStartDelay(500);
        anim1.start();
        anim2.start();
        anim3.start();
        Drawable drawable = done.getDrawable();
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawable;
        avd.start();
        //next_button.setVisibility(View.VISIBLE);
    }
    private void badIp(){
        Toast.makeText(getApplicationContext(), "Błąd połączenia",
                Toast.LENGTH_LONG).show();
        circle.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);

    }
}