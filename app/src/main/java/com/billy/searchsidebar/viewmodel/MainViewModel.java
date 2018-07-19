package com.billy.searchsidebar.viewmodel;

import android.databinding.ObservableArrayList;

import com.billy.searchsidebar.model.SideBarModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Billy_Cui on 2018/7/19
 * Describe:
 */

public class MainViewModel {

    private ObservableArrayList<SideBarModel> items;

    public ObservableArrayList<SideBarModel> getItems() {
        return items;
    }

    public ObservableArrayList<SideBarModel> initData( Consumer<ObservableArrayList<SideBarModel>> consumer){
        Observable.create(new ObservableOnSubscribe<ObservableArrayList<SideBarModel>>() {
            @Override
            public void subscribe(ObservableEmitter<ObservableArrayList<SideBarModel>> emitter) throws Exception {
                items = getChineseSideBarModels();
                emitter.onNext(items);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

        return items;
    }

    public static ObservableArrayList<SideBarModel> getChineseSideBarModels() {
        ObservableArrayList<SideBarModel> items = new ObservableArrayList<>();
        items.add(new SideBarModel("A", "啊啊",""));
        items.add(new SideBarModel("B", "白虎",""));
        items.add(new SideBarModel("C", "常羲",""));
        items.add(new SideBarModel("C", "嫦娥",""));
        items.add(new SideBarModel("E", "二郎神",""));
        items.add(new SideBarModel("F", "伏羲",""));
        items.add(new SideBarModel("G", "观世音",""));
        items.add(new SideBarModel("J", "精卫",""));
        items.add(new SideBarModel("K", "夸父",""));
        items.add(new SideBarModel("N", "女娲",""));
        items.add(new SideBarModel("N", "哪吒",""));
        items.add(new SideBarModel("P", "盘古",""));
        items.add(new SideBarModel("Q", "青龙",""));
        items.add(new SideBarModel("R", "如来",""));
        items.add(new SideBarModel("S", "孙悟空",""));
        items.add(new SideBarModel("S", "沙僧",""));
        items.add(new SideBarModel("S", "顺风耳",""));
        items.add(new SideBarModel("T", "太白金星",""));
        items.add(new SideBarModel("T", "太上老君",""));
        items.add(new SideBarModel("X", "羲和",""));
        items.add(new SideBarModel("X", "玄武",""));
        items.add(new SideBarModel("Z", "猪八戒",""));
        items.add(new SideBarModel("Z", "朱雀",""));
        items.add(new SideBarModel("Z", "祝融",""));
        return items;
    }
}
