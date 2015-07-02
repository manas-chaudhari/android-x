package com.androidx.androidx.service;

import com.androidx.androidx.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventService {

    public List<Event> loadEvents() {

        List<Event> events = new ArrayList<>();
        events.add(new Event());
        return events;
    }
}
