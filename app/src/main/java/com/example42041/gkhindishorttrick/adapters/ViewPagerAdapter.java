package com.example42041.gkhindishorttrick.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example42041.gkhindishorttrick.R;
import com.example42041.gkhindishorttrick.model.DataContent;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    List<DataContent> arrayContentData;
    Context context;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context2, List<DataContent> arrayContentData2) {
        this.context = context2;
        this.arrayContentData = arrayContentData2;
    }

    public int getCount() {
        return this.arrayContentData.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @SuppressLint("WrongConstant")
    public Object instantiateItem(ViewGroup container, int position) {
        this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        View itemView = this.inflater.inflate(R.layout.viewpager_item_content, container, false);
        TextView txtrank = (TextView) itemView.findViewById(R.id.textdata);
        txtrank.setTextSize((float) getTextSize());
        txtrank.setText(this.arrayContentData.get(position).getDataContent());
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

    private int getTextSize() {
        return this.context.getSharedPreferences("appData", 0).getInt("textSize", 18);
    }
}
