package com.androidx.androidx.service;

import android.support.annotation.NonNull;

import com.androidx.androidx.model.Event;

import java.util.ArrayList;
import java.util.List;

import rx.Single;

public class EventService {

    public @NonNull Single<List<Event>> loadEvents() {

        List<Event> events = new ArrayList<>();
        events.add(new Event());
        return Single.just(events);
    }
}
