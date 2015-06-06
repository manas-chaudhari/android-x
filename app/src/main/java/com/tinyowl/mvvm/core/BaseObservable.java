package com.tinyowl.mvvm.core;

import java.util.ArrayList;
import java.util.List;

public class BaseObservable {
    private List<OnChangedListener> mListeners = new ArrayList<>();

    public void notifyChanged() {
        for (OnChangedListener listener : mListeners) {
            listener.onChanged();
        }
    }

    public void addOnChangedListener(OnChangedListener listener) {
        mListeners.add(listener);
    }

    public void removeOnChangedListener(OnChangedListener listener) {
        mListeners.remove(listener);
    }
}
