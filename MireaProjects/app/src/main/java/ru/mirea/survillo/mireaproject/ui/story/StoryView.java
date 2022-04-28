package ru.mirea.survillo.mireaproject.ui.story;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.survillo.mireaproject.MainActivity;
import ru.mirea.survillo.mireaproject.R;

public class StoryView extends AppCompatActivity {
    private EditText number;
    private EditText nameAdd;
    private EditText ageAdd;
    private Button button;
    private AppDatebase db;
    private StoryDao storyDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_view);
        db = App.getInstance().getDatabase();
        storyDao = db.storyDao();

        number = findViewById(R.id.numberStoryEdit);
        nameAdd = findViewById(R.id.NameEdit);
        ageAdd = findViewById(R.id.AgeEdit);

        button = findViewById(R.id.btnSave);
        button.setOnClickListener(this::saveBtn);
    }

    public void saveBtn(View view) {

        Story story =new Story();
        story.number = number.getText().toString();
        story.name = nameAdd.getText().toString();
        story.age = ageAdd.getText().toString();

        storyDao.insert(story);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
