package com.billy.searchsidebar.view;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.billy.searchsidebar.R;
import com.billy.searchsidebar.adapter.RecyclerItemClick;
import com.billy.searchsidebar.databinding.ActivityMainBinding;
import com.billy.searchsidebar.model.SideBarModel;
import com.billy.searchsidebar.utils.NumberUtils;
import com.billy.searchsidebar.viewmodel.MainViewModel;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements RecyclerItemClick, WaveSideBar.OnSelectIndexItemListener, View.OnClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mBinding.setOnClick(this);
        mBinding.setListener(this);
        mBinding.sideBar.setOnSelectIndexItemListener(this);
//        mBinding.AppBarLayout.addOnOffsetChangedListener(new ScrollListenr());
    }

    private void initData() {
        MainViewModel mainViewModel = new MainViewModel();
        mainViewModel.initData(new Consumer<ObservableArrayList<SideBarModel>>() {

            @Override
            public void accept(ObservableArrayList<SideBarModel> sideBarModels) throws Exception {
                mBinding.setList(sideBarModels);
            }
        });
    }

    @Override
    public void onItemClick(SideBarModel sideBarModel) {
        //跳转
    }

    @Override
    public void onSelectIndexItem(String index) {
        mBinding.setSelectIndex(index);
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * appBarLayout 滚动监听
     */
//    class ScrollListenr implements AppBarLayout.OnOffsetChangedListener {
//
//        @Override
//        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//        }
//    }
}
