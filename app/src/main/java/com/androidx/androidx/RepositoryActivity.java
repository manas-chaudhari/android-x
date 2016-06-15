package com.androidx.androidx;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidx.androidx.viewmodel.RepositoryVM;

public class RepositoryActivity extends AppCompatActivity {

    public RepositoryVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new RepositoryVM();
        DataBindingUtil.setContentView(this, R.layout.activity_repository);
    }
}
