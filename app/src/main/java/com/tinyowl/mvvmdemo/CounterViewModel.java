package com.tinyowl.mvvmdemo;

import com.tinyowl.mvvm.core.BasePropertyObservable;
import com.tinyowl.mvvm.core.Command;
import com.tinyowl.mvvm.core.OnChangedListener;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public class CounterViewModel {
    CounterModel model;

    private BehaviorSubject<String> countText = BehaviorSubject.create();

    public String getCountText() {
        return countText.getValue();
    }

    public Observable<String> getCountTextObservable() {
        return countText;
    }

    private void setCountText(String countText) {
        this.countText.onNext(countText);
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
