package com.purplecreations.mygame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Gameovermed extends AppCompatActivity{

    MediaPlayer mysong;
    private TextView DisplayScoremed;
    private Button StartGameagainmed;
    private TextView Maxscoremed;



    private AdView mAdView;
    private String scoring;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameovermed);
        MobileAds.initialize(this,"ca-app-pub-7590533102332942~2310230077");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder( ).build( );
        mAdView.loadAd(adRequest);

        mysong = MediaPlayer.create(Gameovermed.this,R.raw.mymusic);

        DisplayScoremed = findViewById(R.id.scoregotmed);
        StartGameagainmed = findViewById(R.id.startagainmed);
        Maxscoremed = findViewById(R.id.highscoreviewmed);


        int scoremed = getIntent().getIntExtra("Scoremed", 0);
        //scoring = getIntent().getExtras().get("Score").toString();
        DisplayScoremed.setText("Your score:"+ scoremed);



        SharedPreferences settings_med = getSharedPreferences("GAME_DATA_MEDIUM", Context.MODE_PRIVATE);
        int highscoremed = settings_med.getInt("HIGH_SCORE_MEDIUM", 0);

        if(scoremed > highscoremed){
            Maxscoremed.setText("High score : " +scoremed);
            Toast.makeText(Gameovermed.this,"New High Score!",Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = settings_med.edit();
            editor.putInt("HIGH_SCORE_MEDIUM", scoremed);
            editor.commit();

        }else {
            Maxscoremed.setText("High score : "+ highscoremed);
        }








        StartGameagainmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mysong.stop();
                mysong = null;

                Intent intent = new Intent(Gameovermed.this,Gamemenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);




            }



        });






    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(false);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);



            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }


    private void stopPlaying(){
        if(mysong!= null){
            mysong.stop();
            mysong.release();
            mysong = null;
        }
    }
}




