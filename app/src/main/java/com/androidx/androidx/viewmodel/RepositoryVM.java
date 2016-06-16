package com.androidx.androidx.viewmodel;

import com.androidx.androidx.model.Event;

public class RepositoryVM {
    public final String title;

    public RepositoryVM(Event.Repository repository) {
        title = repository.name != null ? repository.name.toUpperCase() : "";
    }
}
