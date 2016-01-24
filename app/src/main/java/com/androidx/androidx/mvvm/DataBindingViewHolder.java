package com.androidx.androidx.mvvm;

import android.databinding.ViewDataBinding;

import com.androidx.androidx.BR;

public class DataBindingViewHolder<VM> extends BindableViewHolder<VM> {

    private final ViewDataBinding mBinding;

    public DataBindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    @Override
    public void bindViewModel(VM vm) {
        mBinding.setVariable(BR.vm, vm);
    }
}
