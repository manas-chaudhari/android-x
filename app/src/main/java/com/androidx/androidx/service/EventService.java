package com.androidx.androidx.service;

import android.support.annotation.NonNull;

import com.androidx.androidx.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;

public class EventService {

    public @NonNull Observable<List<Event>> loadEvents(Scheduler scheduler) {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        return Observable.just(events).delay(2, TimeUnit.SECONDS).subscribeOn(scheduler);
    }
}
