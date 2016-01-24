package com.androidx.androidx.mvvm;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

public class VMRecyclerAdapter<VM> extends RecyclerView.Adapter<BindableViewHolder<VM>> {
    private final ViewProvider<BindableViewHolder<VM>> mViewProvider;
    private List<VM> mViewModels = new ArrayList<>();
    private final Subscription mSubscription;

    public VMRecyclerAdapter(Observable<List<VM>> viewModels, ViewProvider<BindableViewHolder<VM>> viewProvider) {
        this.mViewProvider = viewProvider;
        mSubscription = viewModels.subscribe(vms -> {
            this.mViewModels = vms;
            this.notifyDataSetChanged();
        });
    }

    @Override
    public BindableViewHolder<VM> onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewProvider.createView();
    }

    @Override
    public void onBindViewHolder(BindableViewHolder<VM> holder, int position) {
        VM vm = mViewModels.get(position);
        holder.bindViewModel(vm);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    public void close() {
        mSubscription.unsubscribe();
    }

}
