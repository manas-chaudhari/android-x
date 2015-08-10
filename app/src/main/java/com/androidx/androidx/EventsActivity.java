package com.androidx.androidx;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidx.androidx.mvvm.Binder;
import com.androidx.androidx.service.EventService;
import com.androidx.androidx.viewmodel.EventItemVM;
import com.androidx.androidx.viewmodel.EventsVM;
import com.androidx.androidx.viewmodel.OperationState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EventsActivity extends ActionBarActivity implements EventsVM.OnEventsVMUpdatedListener {
    private EventsVM mViewModel;

    @Bind(R.id.btn_fetch_events)
    public Button fetchButton;

    @Bind(R.id.txt_events_count)
    public TextView countText;

    @Bind(R.id.list_events)
    public ListView eventsListView;

    @Bind(R.id.pb_events)
    public ProgressBar progressBar;

    private ArrayAdapter<EventItemVM> mEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        mEventsAdapter = new ArrayAdapter<EventItemVM>(this, R.layout.abc_simple_dropdown_hint);
        eventsListView.setAdapter(mEventsAdapter);

        // TODO: Check design. Activity is deciding the scheduler
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

    // TODO: Make this private
    public void setViewModel(EventsVM viewModel) {
        this.mViewModel = viewModel;
        this.mViewModel.setListener(this);
        this.onEventsUpdated();

        Binder.bindVisibility(progressBar, mViewModel.getLoadOperationVM().getViewVisibilityObservable(OperationState.RUNNING));
    }

    @Override
    public void onEventsUpdated() {
        countText.setText(getViewModel().getCountText());
        mEventsAdapter.clear();
        mEventsAdapter.addAll(getViewModel().getEventItems());
    }
}
