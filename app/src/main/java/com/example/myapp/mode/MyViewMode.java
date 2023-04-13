package com.example.myapp.mode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewMode extends ViewModel {
    public  int num = 0;
    private MutableLiveData<Integer> liveData;

    public MutableLiveData<Integer> getLiveData() {
        if(liveData==null){
            liveData = new MutableLiveData<>();
            liveData.setValue(0);
        }
        return liveData;
    }
    public void addLiveNum(int n){
        liveData.setValue( liveData.getValue()+n);
    }
}
