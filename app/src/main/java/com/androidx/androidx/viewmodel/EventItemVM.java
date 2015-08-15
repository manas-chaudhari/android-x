package com.androidx.androidx.viewmodel;

import com.androidx.androidx.model.Event;

public class EventItemVM {
    Event mEvent;

    public EventItemVM(Event event) {
        mEvent = event;
    }

    public String getUserLogin() {
        return mEvent.getUserLogin();
    }

    public String getRepositoryName() {
        return mEvent.getRepositoryName();
    }
}
