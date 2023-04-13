package com.example.myapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao  //数据访问接口
public interface WordDao {

    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWord();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    List<Word> getAllWord();


    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordLive();

    @Query("SELECT * FROM WORD WHERE english_word LIKE '%' || :str  || '%' ORDER BY ID DESC")
    LiveData<List<Word>> getLikeWord(String str);
}
