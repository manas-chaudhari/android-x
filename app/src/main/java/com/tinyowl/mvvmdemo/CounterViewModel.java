package com.tinyowl.mvvmdemo;

import com.tinyowl.mvvm.core.BasePropertyObservable;
import com.tinyowl.mvvm.core.Command;
import com.tinyowl.mvvm.core.OnChangedListener;

public class CounterViewModel extends BasePropertyObservable<CounterViewModel.CounterVMProperty> {
    CounterModel model = new CounterModel();

    public enum CounterVMProperty {
        COUNT_TEXT
    }

    private String countText;

    public String getCountText() {
        return countText;
    }

    public void setCountText(String countText) {
        this.countText = countText;
        notifyPropertyChanged(CounterVMProperty.COUNT_TEXT);
    }

    Command incrementCommand = new Command() {
        @Override
        public void run() {
            model.increment();
        }
    };

    Command decrementCommand = new Command() {
        @Override
        public void run() {
            model.decrement();
        }
    };

    public CounterViewModel() {
        model.addOnChangedListener(new OnChangedListener() {
            @Override
            public void onChanged() {
                setCountText(model.getCount() + " \u20b9");
            }
        });
    }
}
