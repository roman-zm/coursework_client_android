package com.namor.coursework.adapter;

import android.view.ViewGroup;

import com.namor.coursework.adapter.diffutil.BindableViewHolder;

public interface ViewHolderCreator {
    BindableViewHolder create(ViewGroup parent);
}
