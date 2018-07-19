package com.billy.searchsidebar.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.billy.searchsidebar.BR;

/**
 * Created by Billy_Cui on 2018/7/19
 * Describe:  列表数据模型
 */

public class SideBarModel implements Observable {

    private String index; //对应字母索引
    private String name;
    private String id;
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    public SideBarModel() {
    }

    public SideBarModel(String index, String name, String id) {
        this.index = index;
        this.name = name;
        this.id = id;
    }


    @Bindable
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
        notifyChange(BR.index);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange(BR.name);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyChange(BR.id);
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }
}
