package com.androidx.androidx.service;

import android.support.annotation.NonNull;

import com.androidx.androidx.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;

public class EventService {
    Scheduler mScheduler;

    public EventService(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    public @NonNull Observable<List<Event>> loadEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        return Observable.just(events).delay(2, TimeUnit.SECONDS).subscribeOn(mScheduler);
    }
}
