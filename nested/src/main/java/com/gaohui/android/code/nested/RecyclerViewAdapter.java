package com.gaohui.android.code.nested;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaohui.android.code.nested.holder.SimpleCategoryViewHolder;
import com.gaohui.android.code.nested.holder.SimpleTextViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> dataList;

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_CATEGORY = 1;

    public RecyclerViewAdapter(ArrayList<Object> list) {
        dataList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_TEXT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_text, viewGroup, false);
            return new SimpleTextViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_category, viewGroup, false);
            return new SimpleCategoryViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object obj = dataList.get(position);
        if(obj != null ) {
            if(obj instanceof String) {
                return TYPE_TEXT;
            } else if(obj instanceof Category) {
                return TYPE_CATEGORY;
            }
        }
        return TYPE_TEXT;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        if(viewHolder instanceof SimpleTextViewHolder) {
            ((TextView)viewHolder.itemView.findViewById(R.id.textView)).setText((String)dataList.get(pos));
        } else if(viewHolder instanceof SimpleCategoryViewHolder) {
            Category category = (Category) dataList.get(pos);
            ((SimpleCategoryViewHolder)viewHolder).refreshData(category.getDataList());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


//    public void setDataList(ArrayList<Object> dataList) {
//        this.dataList = dataList;
//    }
}
