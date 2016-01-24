package com.androidx.androidx.mvvm;

import android.view.View;

public class BasicViewHolder<V extends View & Bindable<VM>, VM> extends BindableViewHolder<VM> {
    public final V itemView;

    public BasicViewHolder(V view) {
        super(view);
        itemView = view;
    }

    public View getView() {
        return itemView;
    }

    @Override
    public void bindViewModel(VM vm) {
        itemView.bindViewModel(vm);
    }
}
