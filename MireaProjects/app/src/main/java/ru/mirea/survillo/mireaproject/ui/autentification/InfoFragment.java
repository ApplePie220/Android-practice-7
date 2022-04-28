package ru.mirea.survillo.mireaproject.ui.autentification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import ru.mirea.survillo.mireaproject.MainActivity;
import ru.mirea.survillo.mireaproject.R;


public class InfoFragment extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private TextView timeView;
    private String host = "time-b.nist.gov";
    private int port = 13;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);
        timeView = view.findViewById(R.id.timeView);

        view.findViewById(R.id.btnTime).setOnClickListener(this::updateInfo);
        return view;
    }


    private void updateInfo(View view){
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String time = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                time = reader.readLine();
                Log.d(TAG, time);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            timeView.setText(time);
        }
    }
}