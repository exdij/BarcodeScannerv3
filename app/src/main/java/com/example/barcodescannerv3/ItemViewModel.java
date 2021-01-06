package com.example.barcodescannerv3;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<Item> mutableItem;
    public MutableLiveData<Item> getMutableItem() {
        if(mutableItem == null) {
            mutableItem = new MutableLiveData<Item>();
        }
        return mutableItem;
    }
}
