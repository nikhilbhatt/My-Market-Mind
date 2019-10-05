package com.example.motivational.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Viewpageradapter extends PagerAdapter {
    private Context mcontext;
    private String[] imageurl;

     public Viewpageradapter(Context mcontext, String[] imageurl)
    {
        this.mcontext=mcontext;
        this.imageurl=imageurl;
    }
    @Override
    public int getCount() {
        return imageurl.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(mcontext);
        Glide.with(mcontext)
                .load(imageurl[position])
                .fitCenter()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);
        return  imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
