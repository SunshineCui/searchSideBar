package com.billy.searchsidebar.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.searchsidebar.R;
import com.billy.searchsidebar.databinding.ItemSearchListBinding;
import com.billy.searchsidebar.model.SideBarModel;

/**
 * Created by Billy_Cui on 2018/7/19
 * Describe:
 */

public class SearchListAdapter extends RecyclerView.Adapter {

    private ObservableArrayList<SideBarModel> mItems ;
    private RecyclerItemClick mListener ;

    public void setItems(ObservableArrayList<SideBarModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public SearchListAdapter(ObservableArrayList<SideBarModel> items) {
        mItems = items;
    }

    public SearchListAdapter(ObservableArrayList<SideBarModel> items, RecyclerItemClick listener) {
        this(items);
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSearchListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_list,parent,false);
        return new SearchListViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemSearchListBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setModel(mItems.get(position));
        if (position == 0){
            binding.setPreviousIndex("");
        }else {
            binding.setPreviousIndex(mItems.get(position-1).getIndex());
        }
        if (mListener !=null)
        binding.setListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class SearchListViewHolder extends RecyclerView.ViewHolder {

        public SearchListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
