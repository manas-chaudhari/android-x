package com.androidx.androidx.mvvm;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BindableViewHolder<VM> extends RecyclerView.ViewHolder implements Bindable<VM> {
    public BindableViewHolder(View itemView) {
        super(itemView);
    }
}
