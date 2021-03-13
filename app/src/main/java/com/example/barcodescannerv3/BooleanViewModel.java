package com.example.barcodescannerv3;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BooleanViewModel extends ViewModel {
    private MutableLiveData<Boolean> mutableBoolean;
    public MutableLiveData<Boolean> getMutableBoolean() {
        if(mutableBoolean == null) {
            mutableBoolean = new MutableLiveData<Boolean>();
        }
        return mutableBoolean;
    }
}
