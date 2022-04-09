package com.ytokmak.catkenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ytokmak.catkenny.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CountDownTimer countDownTimer;
    Handler handler;
    Runnable runnable;
    ImageView[] imageArray;
    int number;
    GridLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        layout = findViewById(R.id.gridLayout);

        imageArray = new ImageView[] {binding.imageView,binding.imageView1,binding.imageView2,binding.imageView3,binding.imageView4,binding.imageView5,
        binding.imageView6, binding.imageView7,binding.imageView8};
        hide();
        number = 0;
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l)
            {
                binding.timeText.setText("Time: " + l / 1000);
            }

            @Override
            public void onFinish() {
                layout.setVisibility(View.INVISIBLE);
                binding.imageView10.setVisibility(View.VISIBLE);
                handler.removeCallbacks(runnable);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Finish");
                alertDialog.setMessage("Wanna play again?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText( MainActivity.this,"AMATERASU", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        }.start();
    }

    public void sum(View view) {
        number++;
        binding.scoreText.setText("Score:"+number);
    }

    public void hide() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (View deneme : imageArray) {
                    deneme.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int numb = random.nextInt(9);
                imageArray[numb].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 400);
            }
        };
        handler.post(runnable);
    }
}

