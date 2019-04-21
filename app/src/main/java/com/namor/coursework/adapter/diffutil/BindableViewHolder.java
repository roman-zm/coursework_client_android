package com.namor.coursework.adapter.diffutil;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.technos.geometr.ui.custom.diffutil.OnPosItemClickListener;

public abstract class BindableViewHolder extends RecyclerView.ViewHolder {

    public BindableViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (onClickListener != null) onClickListener.onClick(v);
            if (onPosItemClickListener != null)
                onPosItemClickListener.onClick(getAdapterPosition());
        });
    }

    public abstract void bindView(DiffUtilable model);

    protected OnPosItemClickListener onPosItemClickListener;

    public void setOnPosItemClickListener(OnPosItemClickListener onPosItemClickListener) {
        this.onPosItemClickListener = onPosItemClickListener;
    }

    protected View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    public ViewHolderListener getHolderListener() { return null; }

    public void setHolderListener(@Nullable ViewHolderListener holderListener) {}
}
