package com.androidx.androidx;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidx.androidx.databinding.ItemEventBinding;
import com.androidx.androidx.mvvm.Binder;
import com.androidx.androidx.mvvm.DataBindingViewHolder;
import com.androidx.androidx.mvvm.VMRecyclerAdapter;
import com.androidx.androidx.service.EventService;
import com.androidx.androidx.viewmodel.EventItemVM;
import com.androidx.androidx.viewmodel.EventsVM;
import com.androidx.androidx.viewmodel.OperationVM;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subjects.BehaviorSubject;


public class EventsActivity extends ActionBarActivity implements EventsVM.OnEventsVMUpdatedListener {
    private EventsVM mViewModel;

    @Bind(R.id.btn_fetch_events)
    public Button fetchButton;

    @Bind(R.id.txt_events_count)
    public TextView countText;

    @Bind(R.id.list_events)
    public RecyclerView eventsListView;

    @Bind(R.id.pb_events)
    public ProgressBar progressBar;

    private VMRecyclerAdapter<EventItemVM> mEventsAdapter;
    private BehaviorSubject<List<EventItemVM>> eventItemVMs = BehaviorSubject.create(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        eventsListView.setLayoutManager(new LinearLayoutManager(this));
        mEventsAdapter = new VMRecyclerAdapter<>(eventItemVMs, () -> new DataBindingViewHolder<>(ItemEventBinding.inflate(LayoutInflater.from(this))));
        eventsListView.setAdapter(mEventsAdapter);

        setViewModel(new EventsVM(new EventService(), this));
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_fetch_events)
    public void fetchEvents() {
        mViewModel.getFetchCommand().execute();
    }

    public EventsVM getViewModel() {
        return mViewModel;
    }

    @VisibleForTesting
    protected void setViewModel(EventsVM viewModel) {
        this.mViewModel = viewModel;
        this.mViewModel.setListener(this);
        this.onEventsUpdated();

        bindOperationVM(mViewModel.getLoadOperationVM());
    }

    private void bindOperationVM(OperationVM operationVM) {
        Binder.bindVisibility(progressBar, operationVM.getRunningViewVisibility());
        Binder.bindVisibility(eventsListView, operationVM.getSuccessfulViewVisibility());
        Binder.bindVisibility(fetchButton, operationVM.getFailedViewVisibility());
    }

    @Override
    public void onEventsUpdated() {
        countText.setText(getViewModel().getCountText());
        eventItemVMs.onNext(getViewModel().getEventItems());
    }
}
