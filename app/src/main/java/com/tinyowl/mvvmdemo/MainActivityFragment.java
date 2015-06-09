package com.tinyowl.mvvmdemo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tinyowl.mvvm.core.PropertyChangedListener;
import com.tinyowl.mvvm.utils.BindUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    CounterViewModel viewModel = new CounterViewModel(new CounterModel());

    @InjectView(R.id.txt_count)
    TextView countTextView;

    @InjectView(R.id.btn_increment)
    Button incrementButton;

    @InjectView(R.id.btn_decrement)
    Button decrementButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, v);
        bindViewModel();
        return v;
    }

    private void bindViewModel() {
        BindUtils.bindClick(incrementButton, viewModel.incrementCommand);
        BindUtils.bindClick(decrementButton, viewModel.decrementCommand);
        BindUtils.bindText(countTextView, viewModel.getCountTextObservable());
    }

}
