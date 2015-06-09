package com.tinyowl.mvvmdemo;

import com.tinyowl.mvvm.core.BasePropertyObservable;
import com.tinyowl.mvvm.core.Command;
import com.tinyowl.mvvm.core.OnChangedListener;

public class CounterViewModel extends BasePropertyObservable<CounterViewModel.CounterVMProperty> {
    CounterModel model;

    public enum CounterVMProperty {
        COUNT_TEXT
    }

    private String countText;

    public String getCountText() {
        return countText;
    }

    private void setCountText(String countText) {
        this.countText = countText;
        notifyPropertyChanged(CounterVMProperty.COUNT_TEXT);
    }

    public Command incrementCommand = new Command() {
        @Override
        public void run() {
            model.increment();
        }
    };

    public Command decrementCommand = new Command() {
        @Override
        public void run() {
            model.decrement();
        }
    };

    public CounterViewModel(CounterModel counterModel) {
        model = counterModel;
        model.addOnChangedListener(new OnChangedListener() {
            @Override
            public void onChanged() {
                update();
            }
        });
        update();
    }

    private void update() {
        setCountText(model.getCount() + " \u20b9");
    }
}
