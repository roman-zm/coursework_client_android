package com.namor.coursework.adapter.diffutil;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class GeoDiffUtilCallback extends DiffUtil.Callback {
    private final List<? extends DiffUtilable> oldList;
    private final List<? extends DiffUtilable> newList;

    public GeoDiffUtilCallback(List<? extends DiffUtilable> oldList,
                               List<? extends DiffUtilable> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        DiffUtilable oldItem = oldList.get(oldItemPosition);
        DiffUtilable newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        DiffUtilable oldItem = oldList.get(oldItemPosition);
        DiffUtilable newItem = newList.get(newItemPosition);
        return oldItem.areContentsTheSame(newItem);
    }

    protected List<? extends DiffUtilable> getOldList() {
        return oldList;
    }

    protected List<? extends DiffUtilable> getNewList() {
        return newList;
    }
}
