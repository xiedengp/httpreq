package com.example.myapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

//这个监听数据更新
public class WordViewMode extends AndroidViewModel {
    private final WordRepository repository;

    public WordViewMode(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
    }


    public LiveData<List<Word>> getLiveData() {
        return repository.getLiveData();
    }

    public LiveData<List<Word>> findWordData(String s) {
        return repository.findWordData(s);
    }

    public void inset(Word... words) {
        repository.inset(words);
    }

    public void update(Word... words) {
        repository.updateWord(words);
    }

    public void deleteWord(Word... words) {
        repository.deleteWord(words);
    }



    public void deleteAll() {
        repository.deleteAll();
    }


}
