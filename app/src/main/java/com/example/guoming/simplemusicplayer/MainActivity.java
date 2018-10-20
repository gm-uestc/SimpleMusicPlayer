package com.example.guoming.simplemusicplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer player = new MediaPlayer() ;
    private Button openBtn ;
    private Button startBtn ;
    private Button pauseBtn ;
    private Button stopBtn ;
    private TextView filePathName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else{
            initMediaPlayer();
        }
    }
    private void initView(){
        openBtn = findViewById(R.id.open);
        startBtn = findViewById(R.id.start);
        pauseBtn = findViewById(R.id.pause);
        stopBtn = findViewById(R.id.stop);
        filePathName = findViewById(R.id.file_path_name);
        openBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    private void initMediaPlayer(){
        try{
            String filename = null;
            File file = null;
            filename = filePathName.getText().toString();
            if(filename != null){
                file = new File(filePathName.getText().toString());
            }else{
                file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            }

            player.setDataSource(file.getPath());
            player.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                    break;
                }else{
                    Toast.makeText(MainActivity.this,"no permissin.",Toast.LENGTH_LONG).show();
                    break;
                }
            }

            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open:
            {

            }
            case R.id.start:
            {
                if(!player.isPlaying()){
                    player.start();
                    break;
                }
            }
            case R.id.pause:
            {
                if(player.isPlaying()){
                    player.pause();
                    break;
                }

            }
            case R.id.stop:
            {
                if(player.isPlaying()){
                    player.reset();
                    initMediaPlayer();
                    break;
                }
            }

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.stop();
            player.release();
        }
    }
}
