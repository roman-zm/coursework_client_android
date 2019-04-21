package com.namor.coursework.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.namor.coursework.adapter.diffutil.BindableViewHolder;
import com.namor.coursework.adapter.diffutil.DiffUtilable;
import com.namor.coursework.adapter.diffutil.ViewHolderListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T extends DiffUtilable> extends RecyclerView.Adapter {
    private SparseArray<ViewHolderCreator> holders = new SparseArray<>();
    private SparseArray<ViewHolderListener> listeners = new SparseArray<>();

    @NonNull
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @NonNull
    protected List<T> items = new ArrayList<>();

    @NonNull
    @Override
    public BindableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BindableViewHolder holder = holders.get(viewType).create(parent);
        holder.setHolderListener(listeners.get(viewType, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BindableViewHolder vh = (BindableViewHolder) holder;
        vh.bindView(getItemAt(position));
    }

    public T getItemAt(int position) {
        if (position >= 0 && position <= items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void registerRenderer(int type, @NonNull ViewHolderCreator creator) {
        holders.put(type, creator);
    }

    @Override
    public abstract int getItemViewType(int position);

    public void setHolderListener(int type, ViewHolderListener listener) {
        listeners.put(type, listener);
    }
}
