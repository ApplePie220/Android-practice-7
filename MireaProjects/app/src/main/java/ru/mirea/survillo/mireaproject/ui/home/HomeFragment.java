package ru.mirea.survillo.mireaproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.survillo.mireaproject.R;
import ru.mirea.survillo.mireaproject.databinding.FragmentHomeBinding;
import ru.mirea.survillo.mireaproject.ui.autentification.Autification;

public class HomeFragment extends Fragment {

    private FirebaseAuth auth;
    private TextView jokeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        auth = FirebaseAuth.getInstance();
        view.findViewById(R.id.sign_out_btn).setOnClickListener(this::onSignOutClick);
        jokeTextView = view.findViewById(R.id.text_home);
        new Thread(this::loadAction).start();
        return view;
    }

    private void onSignOutClick(View view){
        if (auth != null){
            auth.signOut();
            Intent intent = new Intent(getContext(), Autification.class);
            startActivity(intent);
        }
    }

    private void loadAction(){
        try {
            String data = getContentFromApi(
                    "https://www.boredapi.com/api/activity", "GET");
            JSONObject object = new JSONObject(data);
            String action = "If you are bored - " + object.getString("activity");
            jokeTextView.post(() -> jokeTextView.setText(action));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromApi(String address, String method) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod(method);
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = connection.getResponseMessage() + " . Error Code : " + responseCode;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}