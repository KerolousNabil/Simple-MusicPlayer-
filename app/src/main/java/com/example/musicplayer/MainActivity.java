package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayoutCompat linearLayoutCompat;
    RecyclerView recyclerView ;
    TextView musictextview ;
    ArrayList<AudioModel> songsList = new ArrayList<>();
    int PERMISSION_ID = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        recyclerView = findViewById(R.id.recycler_view);
        musictextview = findViewById(R.id.no_songs_text);
        linearLayoutCompat = findViewById(R.id.lineartext);

       checkpermission();
    }

    void checkpermission()
    {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            showYourmusic();
        }else{
            askPermission();
        }

    }

    void askPermission()
      {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_ID);
      }

    void showYourmusic()
    {
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if(songsList.size()==0){
            musictextview.setVisibility(View.VISIBLE);
        }
        else
        {
            //recyclerview
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // recyclerView.setAdapter(new MusicAdapter(songsList,getApplicationContext()));
            recyclerView.setAdapter(new MusicAdapter(songsList, getApplicationContext()));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                checkpermission();

            }
            else {

            linearLayoutCompat.setVisibility(View.VISIBLE);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}