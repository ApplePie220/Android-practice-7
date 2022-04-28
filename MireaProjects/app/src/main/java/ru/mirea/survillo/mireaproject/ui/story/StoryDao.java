package ru.mirea.survillo.mireaproject.ui.story;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM story")
    List<Story> getAll();
    @Query("SELECT * FROM story WHERE id = :id")
    Story getById(long id);
    @Insert
    void insert(Story Story);
    @Update
    void update(Story story);
    @Delete
    void delete(Story story);
}
