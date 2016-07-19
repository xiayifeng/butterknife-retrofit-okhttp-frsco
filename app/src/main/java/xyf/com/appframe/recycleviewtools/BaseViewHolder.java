package xyf.com.appframe.recycleviewtools;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(T data);
}
