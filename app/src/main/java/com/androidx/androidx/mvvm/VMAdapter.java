package com.androidx.androidx.mvvm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class VMAdapter<VM, V extends View & Bindable<VM>> extends BaseAdapter {
    private List<VM> mViewModels;
    private ViewProvider<V> mViewProvider;

    public VMAdapter(Context context, List<VM> viewModels, ViewProvider<V> viewProvider) {
        mViewModels = viewModels;
        mViewProvider = viewProvider;
    }

    @Override
    public int getCount() {
        return mViewModels.size();
    }

    @Override
    public VM getItem(int position) {
        return mViewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V view = (V) convertView;
        if (view == null) {
            view = mViewProvider.createView();
        }

        VM viewModel = getItem(position);
        view.bindViewModel(viewModel);

        return view;
    }

    // TODO: Make this method call notifyDataSetChanged. Not sure how this can be tested
    public void setViewModels(List<VM> viewModels) {
        mViewModels = viewModels;
    }
}
