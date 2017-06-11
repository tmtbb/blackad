package com.yundian.comm.adapter.base;

import java.util.List;

/**
 * Created by yaowang on 16/3/31.
 */
public interface IListAdapter<T> {
    void setList(List<T> list);
    void addData(T t);
    void addList(List<T> list);
    void remove(int index);
    void remove(T t);
    void clear();
    void notifyDataSetChanged();
    List<T> getList();
}
