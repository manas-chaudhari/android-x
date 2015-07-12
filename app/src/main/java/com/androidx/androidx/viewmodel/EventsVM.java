package com.androidx.androidx.viewmodel;

import android.content.Context;

import com.androidx.androidx.R;
import com.androidx.androidx.model.Event;
import com.androidx.androidx.mvvm.Command;
import com.androidx.androidx.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class EventsVM {

    private EventService mEventService;
    private OnEventsVMUpdatedListener mListener;
    private List<Event> mLoadedEvents;
    private Context mContext;

    private Command mFetchCommand = new Command() {
        @Override
        public void execute() {
            setLoadedEvents(mEventService.loadEvents());
        }
    };

    public EventsVM(EventService eventService, Context context) {
        mEventService = eventService;
        mContext = context;
    }

    public Command getFetchCommand() {
        return mFetchCommand;
    }

    public List<EventItemVM> getEventItems() {
        if (mLoadedEvents == null) {
            return new ArrayList<>();
        }
        List<EventItemVM> eventItems = new ArrayList<>();
        for (Event event : mLoadedEvents) {
            eventItems.add(new EventItemVM(event));
        }
        return eventItems;
    }

    private void setLoadedEvents(List<Event> loadedEvents) {
        this.mLoadedEvents = loadedEvents;
        if (mListener != null) mListener.onEventsUpdated();
    }

    public void setListener(OnEventsVMUpdatedListener listener) {
        mListener = listener;
    }

    public String getCountText() {
        List<Event> events = mLoadedEvents;
        if (events == null) {
            return "";
        }

        int n = events.size();
        return mContext.getResources().getQuantityString(R.plurals.numberOfEvents, n, n);
    }

    public interface OnEventsVMUpdatedListener {
        void onEventsUpdated();
    }
}
