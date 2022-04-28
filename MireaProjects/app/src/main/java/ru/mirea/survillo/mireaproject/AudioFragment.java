package ru.mirea.survillo.mireaproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.survillo.mireaproject.ui.PLayingMusic;

public class AudioFragment extends Fragment {
    private ImageButton imageButton;
    private PLayingMusic musicPlayingThread;
    private List<Integer> songsIds = new ArrayList<>();
    private int currentSong = 0;

    @Override
    public void onStop() {
        super.onStop();
        if (musicPlayingThread != null){
            musicPlayingThread.interrupt();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        String[] songsNames =
                getResources().getStringArray(R.array.songs_names);
        for (String name: songsNames){
            songsIds.add(getResources().getIdentifier(
                    "ru.mirea.survillo.mireaproject:raw/"+name,
                    null, null));
        }
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this::onImageButtonClick);

        Button prevSongButton = (Button) view.findViewById(R.id.prev_song_button);
        prevSongButton.setOnClickListener(this::onPrevSongButtonClick);
        Button nextSongButton = (Button) view.findViewById(R.id.next_song_button);
        nextSongButton.setOnClickListener(this::onNextSongButtonClick);
        return view;
    }

    private void onImageButtonClick(View view){
        if(musicPlayingThread == null){
            startSong();
        } else {
            stopPlayer();
        }
    }

    private void startSong(){
        musicPlayingThread = new PLayingMusic(getContext(), songsIds.get(currentSong));
        musicPlayingThread.start();
        imageButton.setImageResource(android.R.drawable.ic_media_pause);
    }

    private void onPrevSongButtonClick(View view){
        stopPlayer();
        currentSong--;
        if (currentSong < 0) currentSong = songsIds.size()-1;
        startSong();
    }

    private void onNextSongButtonClick(View view){
        stopPlayer();
        currentSong++;
        if (currentSong > songsIds.size()-1) currentSong = 0;
        startSong();
    }

    private void stopPlayer(){
        if (musicPlayingThread != null){
            musicPlayingThread.interrupt();
            try {
                musicPlayingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imageButton.setImageResource(android.R.drawable.ic_media_play);
            musicPlayingThread = null;
        }
    }
}