package ru.mirea.survillo.mireaproject.ui.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.mirea.survillo.mireaproject.R;


public class StorysFragment extends Fragment {

    static int num =0;
    private List<Story> storys;
    private RecyclerView recyclerView;
    private Button newStory;
    private StoryDao storyDao;
    private AppDatebase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = App.getInstance().getDatabase();

        storyDao = db.storyDao();
        storys = storyDao.getAll();

        Log.d("xyq", String.valueOf(storys.size()));
        View view = inflater.inflate(R.layout.fragment_storys, container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        for (Story it: storys){
            Log.d("StoriesFragment", it.number);
        }
        newStory = view.findViewById(R.id.button);
        newStory.setOnClickListener(this::onClick);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        StoryAdapter adapter = new StoryAdapter(storys);
        recyclerView.setAdapter(adapter);

        return view;
    }


    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), StoryView.class);
        startActivity(intent);

    }
}