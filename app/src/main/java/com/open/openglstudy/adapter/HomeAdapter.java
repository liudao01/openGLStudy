package com.open.openglstudy.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.open.openglstudy.R;
import com.open.openglstudy.bean.ActivityBean;

import java.util.List;


public class HomeAdapter extends BaseQuickAdapter<ActivityBean, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityBean item) {
        helper.setText(R.id.text, item.getDescribe());
    }
}
