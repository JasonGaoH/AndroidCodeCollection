package com.gaohui.android.code.nested.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gaohui.android.code.nested.CategoryAdapter;
import com.gaohui.android.code.nested.R;
import com.gaohui.android.code.nested.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleCategoryViewHolder extends RecyclerView.ViewHolder {

    public RecyclerView categoryRecyclerView;

    private CategoryAdapter recyclerViewAdapter;

    public SimpleCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        if(itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams)itemView.getLayoutParams()).setFullSpan(true);
        }
        categoryRecyclerView = itemView.findViewById(R.id.categoryRecyclerView);
//        categoryRecyclerView.setNestedScrollingEnabled(false);
        ArrayList<Object> data = new ArrayList<>();
        recyclerViewAdapter = new CategoryAdapter(data);
        categoryRecyclerView.setAdapter(recyclerViewAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    public void refreshData(ArrayList<Object> dataList) {
        recyclerViewAdapter.setDataList(dataList);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
