package com.billy.searchsidebar.adapter;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.billy.searchsidebar.model.SideBarModel;

/**
 * Created by Billy_Cui on 2018/7/19
 * Describe:
 */

public class ViewBindAdapter {

    @BindingAdapter(value = {"items", "listener"})
    public static void setSearchListAdapter(RecyclerView rv, ObservableArrayList<SideBarModel> items, RecyclerItemClick listener) {
        RecyclerView.Adapter adapter = rv.getAdapter();
        if (adapter == null) {
            adapter = new SearchListAdapter(items == null ? new ObservableArrayList<SideBarModel>() : items, listener);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            ((SearchListAdapter)adapter).setItems(items);
        }
    }

    @BindingAdapter(value = {"Items", "selectIndex"})
    public static void selectIndex(RecyclerView rv, ObservableArrayList<SideBarModel> Items, String selectIndex) {
        if (selectIndex == null || selectIndex.equals("")){
            return;
        }
        for (int i = 0; i < Items.size(); i++) {
            if (Items.get(i).getIndex().equals(selectIndex)){
                ((LinearLayoutManager) rv.getLayoutManager()).scrollToPositionWithOffset(i,0);
                return;
            }
        }
    }

}
