package com.androidx.androidx.service;

import android.support.annotation.NonNull;

import com.androidx.androidx.model.Event;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class EventService {

    public @NonNull Observable<List<Event>> loadEvents() {

        List<Event> events = new ArrayList<>();
        events.add(new Event());
        return Observable.just(events);
    }
}
