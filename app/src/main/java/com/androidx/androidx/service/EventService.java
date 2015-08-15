package com.androidx.androidx.service;

import android.support.annotation.NonNull;

import com.androidx.androidx.model.Event;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Scheduler;

public class EventService {

    public @NonNull Observable<List<Event>> loadEvents(Scheduler scheduler) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        GithubHttpService httpService = restAdapter.create(GithubHttpService.class);
        return httpService.listEvents().subscribeOn(scheduler);
    }
}
