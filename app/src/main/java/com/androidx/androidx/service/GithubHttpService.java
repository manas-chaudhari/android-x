package com.androidx.androidx.service;

import com.androidx.androidx.model.Event;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface GithubHttpService {

    @GET("/events")
    Observable<List<Event>> listEvents();
}
