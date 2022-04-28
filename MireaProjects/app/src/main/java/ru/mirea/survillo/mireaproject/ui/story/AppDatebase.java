package ru.mirea.survillo.mireaproject.ui.story;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Story.class}, version = 2)
public abstract class AppDatebase extends RoomDatabase{
    public abstract StoryDao storyDao();
}
