package com.baidingapp.faceapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AllInputRateAdapter extends BaseQuickAdapter<AllInputRateModel, BaseViewHolder>{

    public AllInputRateAdapter(@LayoutRes int layoutResId, @Nullable List<AllInputRateModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllInputRateModel item) {
        // ChainRule works
        helper.setText(R.id.item_all_input_rate_tv, item.getNumber());
        //      .setImageResource(R.id.item_all_input_rate_iv, R.mipmap.ic_launcher);

        // Get the Photo using Glide
        Glide.with(mContext).load(item.getPhotoUrl()).into((ImageView) helper.getView(R.id.item_all_input_rate_iv));

        // Get the Current Position
        //int position = helper.getLayoutPosition();
    }
}
