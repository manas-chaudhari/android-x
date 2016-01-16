package com.androidx.androidx.mvvm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

public class VMRecyclerAdapter<V extends View & Bindable<VM>, VM> extends RecyclerView.Adapter<VMRecyclerAdapter.BasicViewHolder<V>> {
    private final ViewProvider<V> mViewProvider;
    private List<VM> mViewModels = new ArrayList<>();
    private final Subscription mSubscription;

    public VMRecyclerAdapter(Observable<List<VM>> viewModels, ViewProvider<V> viewProvider) {
        this.mViewProvider = viewProvider;
        mSubscription = viewModels.subscribe(vms -> {
            this.mViewModels = vms;
            this.notifyDataSetChanged();
        });
    }

    @Override
    public BasicViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BasicViewHolder<>(mViewProvider.createView());
    }

    @Override
    public void onBindViewHolder(BasicViewHolder<V> holder, int position) {
        VM vm = mViewModels.get(position);
        holder.itemView.bindViewModel(vm);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    public void close() {
        mSubscription.unsubscribe();
    }

    public static class BasicViewHolder<V extends View> extends RecyclerView.ViewHolder {
        public final V itemView;

        public BasicViewHolder(V view) {
            super(view);
            itemView = view;
        }
    }

}
