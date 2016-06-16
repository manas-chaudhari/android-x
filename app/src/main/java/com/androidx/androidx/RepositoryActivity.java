package com.androidx.androidx;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidx.androidx.model.Event;
import com.androidx.androidx.mvvm.Bindable;
import com.androidx.androidx.viewmodel.RepositoryVM;

public class RepositoryActivity extends AppCompatActivity implements Bindable<RepositoryVM> {

    public RepositoryVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RepositoryVM vm = new RepositoryVM(new Event.Repository());
        DataBindingUtil.setContentView(this, R.layout.activity_repository);
        bindViewModel(vm);
    }

    public void bindViewModel(@NonNull RepositoryVM vm) {
        this.vm = vm;
        setTitle(vm.title);
    }
}
